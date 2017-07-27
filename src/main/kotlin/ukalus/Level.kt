package ukalus

import ukalus.math.Vector2D

import java.io.Serializable
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.LinkedList
import java.util.Random
import java.util.function.Function
import java.util.function.Predicate

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Level
/**
 * Creates a new Level object.

 * @param name
 * *          DOCUMENT ME!
 */
(
        /**
         * Returns the name.

         * @return String
         */
        val name: String) : Serializable {
    private var tiles: Array<Array<Tile?>>? = null
    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    val height = 20
    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    val width = 80
    private var randomizer: Random? = null
    /**
     * Returns the active.

     * @return boolean
     */
    /**
     * Sets the active.

     * @param active
     * *          The active to set
     */
    var isActive: Boolean = false

    init {
        tiles = Array<Array<Tile?>>(height) { arrayOfNulls<Tile>(width) }
        randomizer = Random()
    }

    /**
     * DOCUMENT ME!
     */
    fun run() {
        val list = LinkedList<InitiativeCell>()

        for (x in 0..height - 1) {
            for (y in 0..width - 1) {
                if (tiles!![x][y]?.tileType is Floor) {
                    val current = (tiles!![x][y]?.tileType as Floor).creature

                    if (current != null) {
                        // REDTAG - invented quickness. Need to get from creature.
                        var initiative = roll!!.roll(3)

                        do {
                            list.add(InitiativeCell(initiative, current))
                            initiative -= 20
                        } while (initiative > 0)
                    }
                }
            }
        }

        Collections.sort(list, ReverseIntegerComparator())

        for (index in list.indices) {
            val creature = list[index]
                    .creature

            if (creature.level == this) {
                creature.activate()
            }
        }
    }

    /**
     * DOCUMENT ME!

     * @param coordinate
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    operator fun get(coordinate: Vector2D): Tile? {
        val x = coordinate.x
        val y = coordinate.y
        var result: Tile? = null

        if (x < 0 || x > height - 1 || y < 0 || y > width - 1) {
            result = null
        } else {
            result = tiles!![x][y]
        }

        return result
    }

    /**
     * DOCUMENT ME!

     * @param predicate
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    fun getRandom(predicate: Predicate<Tile?>): Tile? {
        var result: Tile? = null
        val candidates = ArrayList<Tile>()

        for (x in 0..height - 1) {
            for (y in 0..width - 1) {
                if (predicate.test(tiles!![x][y])) {
                    candidates.add(tiles!![x][y]!!)
                }
            }
        }

        if (candidates.size > 0) {
            result = candidates[randomizer!!.nextInt(candidates.size)]
        }

        return result
    }

    operator fun set(coordinate: Vector2D, type: TileType?) {
        if (type == null) {
            tiles!![coordinate.x][coordinate.y] = null
        } else {
            tiles!![coordinate.x][coordinate.y] = Tile(
                    coordinate, type)
        }
    }

    operator fun contains(currentCoordinate: Vector2D): Boolean {
        return currentCoordinate.x >= 0
                && currentCoordinate.x < height - 1
                && currentCoordinate.y >= 0
                && currentCoordinate.y < width - 1
    }

    private inner class InitiativeCell(
            /**
             * Returns the initiative.

             * @return int
             */
            val initiative: Int,
            /**
             * Returns the creature.

             * @return Creature
             */
            val creature: Creature)

    private inner class ReverseIntegerComparator : Comparator<InitiativeCell> {

        /**
         * @see java.util.Comparator.compare
         */
        override fun compare(o1: InitiativeCell, o2: InitiativeCell): Int {
            val rank1 = o1.initiative
            val rank2 = o2.initiative

            return if (rank1 < rank2) 1 else if (rank1 > rank2) -1 else 0
        }
    }

    companion object {

        private const val serialVersionUID = 3834023645663016245L
        private var roll: Roll? = null

        init {
            roll = Roll()
        }
    }
}