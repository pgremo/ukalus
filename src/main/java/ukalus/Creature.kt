package ukalus

import ukalus.math.Vector2D

import java.io.Serializable
import java.util.HashMap
import java.util.LinkedList

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Creature : Serializable {
    private val lock = java.lang.Object()

    private var properties: MutableMap<Any, Any>? = null
    /**
     * Returns the name.

     * @return String
     */
    /**
     * Sets the name.

     * @param value
     * *          The name to set
     */
    var name: String? = null
    /**
     * Returns the coordinate.

     * @return Coordinate
     */
    /**
     * Sets the coordinate.

     * @param coordinate
     * *          The coordinate to set
     */
    var coordinate: Vector2D? = null
    private var client: Client? = null
    private var levelChanged: Boolean = false
    /**
     * Returns the level.

     * @return Level
     */
    /**
     * Sets the level.

     * @param level
     * *          The level to set
     */
    var level: Level? = null
        set(level) {
            field = level
            levelChanged = true
        }
    private var things: MutableList<Thing>? = null
    private var power: Int = 0

    init {
        properties = HashMap<Any, Any>()
        things = LinkedList<Thing>()
    }

    /**
     * Creates a new Creature object.

     * @param client
     * *          DOCUMENT ME!
     */
    fun setClient(client: Client) {
        this.client = client
    }

    /**
     * DOCUMENT ME!
     */
    @Synchronized fun activate() {
        if (levelChanged) {
            client!!.onLevelChange(this.level!!)
            levelChanged = false
        }

        client!!.onVisionChange(vision)

        power = 1

        while (power > 0) {
            try {
                lock.wait()
            } catch (e: InterruptedException) {
            }

        }
    }

    /**
     * DOCUMENT ME!

     * @param command
     * *          DOCUMENT ME!
     */
    @Synchronized fun executeCommand(command: Command) {
        val type = command.type

        when (type) {
            CommandType.MOVE -> Referee.move(this, command.parameter as Vector2D)
            CommandType.OPEN -> Referee.open(this, command.parameter as Door?)
            CommandType.CLOSE -> Referee.close(this, command.parameter as Door?)
            CommandType.UP -> Referee.up(this)
            CommandType.DOWN -> Referee.down(this)
            CommandType.QUIT -> Referee.quit(this)
            CommandType.SAVE -> Referee.save(this)
            CommandType.PICKUP -> Referee.pickup(this, command.parameter as Thing)
            CommandType.DROP -> Referee.drop(this, command.parameter as Thing)
        }

        power--
        lock.notify()
    }

    /**
     * DOCUMENT ME!

     * @param key
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    fun getProperty(key: Any): Any? {
        return properties!![key]
    }

    /**
     * DOCUMENT ME!

     * @param key
     * *          DOCUMENT ME!
     * *
     * @param value
     * *          DOCUMENT ME!
     */
    fun setProperty(key: Any, value: Any) {
        properties!!.put(key, value)
    }

    /**
     * DOCUMENT ME!

     * @param key
     * *          DOCUMENT ME!
     */
    fun removeProperty(key: Any) {
        properties!!.remove(key)
    }

    /**
     * DOCUMENT ME!

     * @param thing
     * *          DOCUMENT ME!
     */
    fun addThing(thing: Thing) {
        things!!.add(thing)
    }

    /**
     * DOCUMENT ME!

     * @param thing
     * *          DOCUMENT ME!
     */
    fun removeThing(thing: Thing) {
        things!!.remove(thing)
    }

    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    fun getThings(): Iterator<Thing> {
        return things!!.iterator()
    }

    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    val vision: List<Tile>
        get() = if (coordinate == null) emptyList() else fov.getSeen(level, coordinate!!.x, coordinate!!.y, 3)

    companion object {

        private const val serialVersionUID = 3257003250497630515L

        private val fov = RecursiveShadowCastingVision()
    }
}