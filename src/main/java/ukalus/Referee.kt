package ukalus

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.level.dungeon.recursive.RecursiveDungeonGenerator
import ukalus.math.Vector2D
import ukalus.persistence.Persistence
import ukalus.persistence.PersistenceException

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
object Referee {

    private val CREATURE = "creature"
    private val stairsPredicate: StairsPredicate = StairsPredicate()
    private val generator: RecursiveDungeonGenerator = RecursiveDungeonGenerator(RandomAdaptor(MersenneTwister(System.currentTimeMillis())))
    private var run: Boolean = false

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    fun run(creature: Creature): Boolean {
        run = true

        while (run) {
            val level = creature.level
            level!!.run()

            if (!level.isActive) {
                try {
                    Persistence.put(level.name, level)
                } catch (e: Exception) {
                }

            }
        }

        return run
    }

    /**
     * DOCUMENT ME!
     */
    fun stop() {
        run = false
    }

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     */
    fun down(creature: Creature) {
        val sourceLocation = creature.coordinate
        val sourceLevel = creature.level
        val tile = sourceLevel!![sourceLocation!!]
        val type = tile!!.tileType

        if (type is Floor) {
            val floor = type
            val portal = floor.portal

            if (portal is Stairs) {
                val sourceStairs = portal as Stairs?

                if (Stairs.DOWN == sourceStairs!!.direction) {
                    var targetLocation: Vector2D = sourceStairs.coordinate!!
                    var targetName = sourceStairs.levelName
                    val targetTile: Tile?
                    var targetLevel: Level? = null

                    if (targetName == null) {
                        val sourceName = sourceLevel.name
                        targetName = Integer.toString(Integer.parseInt(sourceName) + 1)
                        targetLevel = generator.generate(targetName)
                        stairsPredicate.direction = Stairs.UP
                        targetTile = targetLevel!!.getRandom(stairsPredicate)

                        val targetStairs = (targetTile!!.tileType as Floor).portal as Stairs?
                        targetStairs!!.coordinate = sourceLocation
                        targetStairs.levelName = sourceName
                        targetLocation = targetTile.location
                        sourceStairs.coordinate = targetLocation
                        sourceStairs.levelName = targetName
                    } else {
                        try {
                            targetLevel = Persistence[targetName] as Level?
                        } catch (e: Exception) {
                        }

                        targetTile = targetLevel!![targetLocation]
                    }

                    floor.creature = null
                    (targetTile!!.tileType as Floor).creature = creature
                    creature.coordinate = targetLocation
                    creature.level = targetLevel
                    targetLevel.isActive = true
                    sourceLevel.isActive = false
                }
            }
        }
    }

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     */
    fun up(creature: Creature) {
        val sourceLocation = creature.coordinate
        val sourceLevel = creature.level
        val tile = sourceLevel!![sourceLocation!!]
        val type = tile!!.tileType

        if (type is Floor) {
            val floor = type
            val portal = floor.portal

            if (portal is Stairs) {
                val sourceStairs = portal as Stairs?

                if (Stairs.UP == sourceStairs!!.direction) {
                    var targetLocation: Vector2D = sourceStairs.coordinate!!
                    var targetName = sourceStairs.levelName
                    val targetTile: Tile?
                    var targetLevel: Level? = null

                    if (targetName == null) {
                        val sourceName = sourceLevel.name
                        targetName = Integer.toString(Integer.parseInt(sourceName) + 1)
                        targetLevel = generator.generate(targetName)
                        stairsPredicate.direction = Stairs.DOWN
                        targetTile = targetLevel!!.getRandom(stairsPredicate)

                        val targetStairs = (targetTile!!.tileType as Floor).portal as Stairs?
                        targetStairs!!.coordinate = sourceLocation
                        targetStairs.levelName = sourceName
                        targetLocation = targetTile.location
                        sourceStairs.coordinate = targetLocation
                        sourceStairs.levelName = targetName
                    } else {
                        try {
                            targetLevel = Persistence[targetName] as Level?
                        } catch (e: Exception) {
                        }

                        targetTile = targetLevel!![targetLocation]
                    }

                    floor.creature = null
                    (targetTile!!.tileType as Floor).creature = creature
                    creature.coordinate = targetLocation
                    creature.level = targetLevel
                    targetLevel.isActive = true
                    sourceLevel.isActive = false
                }
            }
        }
    }

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     * *
     * @param direction
     * *          DOCUMENT ME!
     */
    fun move(creature: Creature, direction: Vector2D) {
        val oldCoordinate = creature.coordinate
        val coordinate = oldCoordinate!!.plus(direction)
        val level = creature.level
        val tile = level!![coordinate]
        val type = tile!!.tileType

        if (type is Floor) {
            val floor = type
            val door = floor.door

            if (door == null || door.isOpen) {
                creature.coordinate = coordinate
                floor.creature = creature

                val oldTile = level[oldCoordinate]
                val oldFloor = oldTile!!.tileType as Floor
                oldFloor.creature = null
            }
        }
    }

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     * *
     * @param thing
     * *          DOCUMENT ME!
     */
    fun pickup(creature: Creature, thing: Thing) {
        val coordinate = creature.coordinate
        val level = creature.level
        val tile = level!![coordinate!!]
        val type = tile!!.tileType

        if (type is Floor) {
            creature.addThing(thing)
            thing.owner = creature
            type.removeThing(thing)
        }
    }

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     * *
     * @param thing
     * *          DOCUMENT ME!
     */
    fun drop(creature: Creature, thing: Thing) {
        val coordinate = creature.coordinate
        val level = creature.level
        val tile = level!![coordinate!!]
        val type = tile!!.tileType

        if (type is Floor) {
            creature.removeThing(thing)
            thing.owner = null
            type.addThing(thing)
        }
    }

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     * *
     * @param door
     * *          DOCUMENT ME!
     */
    fun open(creature: Creature, door: Door?) {
        if (door != null && !door.isOpen) {
            door.open()
        }
    }

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     * *
     * @param door
     * *          DOCUMENT ME!
     */
    fun close(creature: Creature, door: Door?) {
        if (door != null && door.isOpen) {
            door.close()
        }
    }

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     */
    fun quit(creature: Creature) {
        Persistence.delete(creature.name!!)
        stop()
    }

    /**
     * DOCUMENT ME!

     * @param name
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    fun load(name: String): Creature {
        Persistence.open(name)
        return Persistence[CREATURE] as Creature
    }

    /**
     * DOCUMENT ME!

     * @param creature
     * *          DOCUMENT ME!
     */
    fun save(creature: Creature) {
        try {
            Persistence.put(CREATURE, creature)
        } catch (e: PersistenceException) {
            e.printStackTrace()
        }

        stop()
    }

}