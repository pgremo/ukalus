package ukalus.ui.jcurses

import jcurses.event.ActionEvent
import jcurses.event.ActionListener
import jcurses.event.ActionListenerManager
import jcurses.system.CharColor
import jcurses.system.InputChar
import jcurses.system.Toolkit
import jcurses.util.Rectangle
import jcurses.widgets.Widget
import ukalus.*
import ukalus.math.Vector2D
import java.util.*

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class JCursesClient
/**
 * Creates a new JCursesClient object.

 * @param creature
 * *          DOCUMENT ME!
 */
(@Transient private val hero: Creature) : Widget(), Client {

    @Transient private val manager = ActionListenerManager()
    @Transient private var vision: MutableList<Tile>? = null
    @Transient private var plan: Plan? = null
    @Transient private var commandType: CommandType? = null
    @Transient private var command: Command? = null
    @Transient private var commandReady: Boolean = false
    @Transient private var planBook: PlanBook? = null

    init {
        planBook = hero.getProperty("planBook") as PlanBook

        if (planBook == null) {
            planBook = PlanBook()
            hero.setProperty("planBook", planBook)
        }

        onLevelChange(hero.level)
    }

    /**
     * DOCUMENT ME!

     * @param level
     * *          DOCUMENT ME!
     */
    override fun onLevelChange(level: Level) {
        val name = level.name + "-plan"
        planBook!!.turnTo(name)
        plan = planBook!!.get() as Plan

        if (plan == null) {
            plan = Plan(name, level.height, level.width)
            planBook!!.add(name, plan!!)
        }

        vision = null
    }

    /**
     * DOCUMENT ME!

     * @param list
     * *          DOCUMENT ME!
     */
    override fun onVisionChange(list: MutableList<Tile>) {
        if (vision != null) {
            vision!!.removeAll(list)
        }

        updateFromPlan(vision)

        vision = list
        updatePlan(vision!!)
        updateFromVision(vision)
    }

    /**
     * DOCUMENT ME!

     * @param list
     * *          DOCUMENT ME!
     */
    private fun updatePlan(list: List<Tile>) {

        for (current in list) {
            plan!![current.location] = current.tileType
        }
    }

    /**
     * DOCUMENT ME!

     * @param list
     * *          DOCUMENT ME!
     */
    private fun updateFromPlan(list: List<Tile>?) {
        val colors = CharColor(CharColor.BLACK, CharColor.WHITE,
                CharColor.NORMAL)

        if (list == null) {
            val buffer = StringBuilder()

            for (x in 0..plan!!.height - 1) {
                for (y in 0..plan!!.width - 1) {
                    buffer.append(symbols[determineLevelMarker(plan!!.get(Vector2D(x, y)))])
                }

                buffer.append("\n")
            }

            val area = Rectangle(absoluteX, absoluteY, plan!!.width, plan!!.height)
            Toolkit.printString(buffer.toString(), area, colors)
        } else {

            for (tile in list) {
                val coordinate = tile.location
                Toolkit.printString(
                        symbols[determineLevelMarker(plan!![coordinate])],
                        absoluteX + coordinate.y, absoluteY + coordinate.x, colors)
            }
        }
    }

    /**
     * DOCUMENT ME!

     * @param list
     * *          DOCUMENT ME!
     */
    private fun updateFromVision(list: List<Tile>?) {
        if (list != null) {
            val colors = CharColor(CharColor.BLACK, CharColor.WHITE,
                    CharColor.BOLD)

            for (tile in list) {
                val (x, y) = tile.location
                val type = tile.tileType
                var marker: Marker? = determineCreatureMarker(type)

                if (marker == null) {
                    marker = determineLevelMarker(type)
                }

                Toolkit.printString(symbols[marker], absoluteX + y,
                        absoluteY + x, colors)
            }
        }
    }

    /**
     * @see jcurses.widgets.Widget.handleInput
     */
    override fun handleInput(inputChar: InputChar?): Boolean {
        val code = inputChar!!.code
        val direction = directions[code]

        if (direction != null) {
            val coordinate = hero.coordinate
            val type = plan!![coordinate.plus(direction)]

            if (type is Floor) {
                val door = type.door

                if (CommandType.CLOSE == commandType && door != null
                        && door.isOpen) {
                    command = Command(commandType, door)
                    commandReady = true
                } else if (CommandType.OPEN == commandType && door != null
                        && !door.isOpen) {
                    command = Command(commandType, door)
                    commandReady = true
                } else if (door == null || door.isOpen) {
                    command = Command(CommandType.MOVE, direction)
                    commandReady = true
                } else if (!door.isOpen) {
                    command = Command(CommandType.OPEN, door)
                    commandReady = true
                } else {
                    commandType = null
                    command = null
                    commandReady = false
                }
            }
        } else {
            val current = commandTypes[code]

            if (CommandType.UP == current) {
                command = Command(current, null)
                commandReady = true
            } else if (CommandType.DOWN == current) {
                command = Command(current, null)
                commandReady = true
            } else if (CommandType.QUIT == current) {
                command = Command(current, null)
                commandReady = true
            } else if (CommandType.WAIT == current) {
                command = Command(current, null)
                commandReady = true
            } else if (CommandType.INVENTORY == current) {
                val inventory = Inventory(hero)
                inventory.show()
            } else if (CommandType.SAVE == current) {
                command = Command(current, null)
                commandReady = true
            } else if (CommandType.PICKUP == current) {
                val coordinate = hero.coordinate
                val type = plan!![coordinate]

                if (type is Floor) {
                    val floor = type

                    if (floor.thingCount > 0) {
                        val thing: Thing?

                        if (floor.thingCount == 1) {
                            thing = floor.things
                                    .next()
                        } else {
                            val pickup = Pickup(floor)
                            thing = pickup.selectThing()
                        }

                        if (thing != null) {
                            command = Command(current, thing)
                            commandReady = true
                        }
                    }
                }
            } else if (CommandType.DROP == current) {
                if (hero.things
                        .hasNext()) {
                    val inventory = Inventory(hero)
                    inventory.show()

                    val thing = inventory.selected

                    if (thing != null) {
                        command = Command(current, thing)
                        commandReady = true
                    }
                }
            } else {
                commandType = current
                commandReady = false
            }
        }

        if (commandReady) {
            val result = command
            commandType = null
            command = null
            commandReady = false

            log(result!!.type
                    .name)
            hero.executeCommand(result)
        }

        return true
    }

    /**
     * DOCUMENT ME!

     * @param message
     * *          DOCUMENT ME!
     */
    private fun log(message: String) {
        val colors = CharColor(CharColor.BLACK, CharColor.WHITE)
        Toolkit.printString(message, 0, 0, colors)
    }

    /**
     * DOCUMENT ME!

     * @param type
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    private fun determineCreatureMarker(type: TileType?): Marker? {
        var result: Marker? = null

        if (type != null && type is Floor) {
            val floor = type as Floor?
            val creature = floor!!.creature

            if (creature === hero) {
                result = Marker.HERO
            }
        }

        return result
    }

    /**
     * DOCUMENT ME!

     * @param type
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    private fun determineLevelMarker(type: TileType?): Marker? {
        var result: Marker? = null

        if (type != null) {
            val objects = LinkedList<Any>()
            objects.add(type)

            if (type is Floor) {
                val floor = type as Floor?

                if (floor!!.portal != null) {
                    objects.add(floor.portal)
                }

                if (floor.door != null) {
                    objects.add(floor.door)
                }

                val iterator = floor.things

                while (iterator.hasNext()) {
                    objects.add(iterator.next())
                }
            }

            val top = objects.last

            if (top is Wall) {
                result = Marker.WALL
            } else if (top is Floor) {
                result = Marker.FLOOR
            } else if (top is Weapon) {
                result = Marker.WEAPON
            } else if (top is Door) {
                if (top.isOpen) {
                    result = Marker.DOOR_OPEN
                } else {
                    result = Marker.DOOR_CLOSED
                }
            } else if (top is Stairs) {
                if (top.direction == Stairs.DOWN) {
                    result = Marker.STAIRS_DOWN
                } else {
                    result = Marker.STAIRS_UP
                }
            }
        }

        return result
    }

    /**
     * @see jcurses.widgets.Widget.doPaint
     */
    override fun doPaint() {
        doRepaint()
    }

    /**
     * @see jcurses.widgets.Widget.doRepaint
     */
    override fun doRepaint() {
        updateFromPlan(null)
        updateFromVision(vision)
    }

    /**
     * @see jcurses.widgets.Widget.getPreferredSize
     */
    override fun getPreferredSize(): Rectangle {
        return Rectangle(0, 0, Toolkit.getScreenWidth(),
                Toolkit.getScreenHeight())
    }

    /**
     * DOCUMENT ME!
     */
    fun setGameEnd() {
        val event = ActionEvent(this)
        manager.handleEvent(event)
    }

    /**
     * DOCUMENT ME!

     * @param listener
     * *          DOCUMENT ME!
     */
    fun addActionListener(listener: ActionListener) {
        manager.addListener(listener)
    }

    /**
     * DOCUMENT ME!

     * @param listener
     * *          DOCUMENT ME!
     */
    fun removeActionListener(listener: ActionListener) {
        manager.removeListener(listener)
    }

    /**
     * @see jcurses.widgets.Widget.isFocusable
     */
    override fun isFocusable(): Boolean {
        return true
    }

    companion object {

        private var directions = mapOf(
                InputChar.KEY_UP to Vector2D(-1, 0),
                InputChar.KEY_RIGHT to Vector2D(0, 1),
                InputChar.KEY_DOWN to Vector2D(1, 0),
                InputChar.KEY_LEFT to Vector2D(0, -1),
                '8'.toInt() to Vector2D(-1, 0),
                '9'.toInt() to Vector2D(-1, 1),
                '6'.toInt() to Vector2D(0, 1),
                '3'.toInt() to Vector2D(1, 1),
                '2'.toInt() to Vector2D(1, 0),
                '1'.toInt() to Vector2D(1, -1),
                '4'.toInt() to Vector2D(0, -1),
                '7'.toInt() to Vector2D(-1, -1)
        )
        private var commandTypes = mapOf(
                'c'.toInt() to CommandType.CLOSE,
                'o'.toInt() to CommandType.OPEN,
                '<'.toInt() to CommandType.UP,
                '>'.toInt() to CommandType.DOWN,
                'Q'.toInt() to CommandType.QUIT,
                'S'.toInt() to CommandType.SAVE,
                ','.toInt() to CommandType.PICKUP,
                'd'.toInt() to CommandType.DROP,
                '.'.toInt() to CommandType.WAIT,
                'i'.toInt() to CommandType.INVENTORY
        )
        private var symbols = mapOf(
                null to " ",
                Marker.WALL to "#",
                Marker.DOOR_CLOSED to "+",
                Marker.DOOR_OPEN to "/",
                Marker.FLOOR to ".",
                Marker.STAIRS_DOWN to ">",
                Marker.STAIRS_UP to "<",
                Marker.WEAPON to "(",
                Marker.HERO to "@"
        )
    }
}