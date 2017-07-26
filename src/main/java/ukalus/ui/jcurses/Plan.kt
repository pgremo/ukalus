package ukalus.ui.jcurses

import ukalus.Tile
import ukalus.TileType
import ukalus.math.Vector2D

import java.io.Serializable

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Plan
/**
 * Creates a new Map object.

 * @param height
 * *          DOCUMENT ME!
 * *
 * @param width
 * *          DOCUMENT ME!
 */
(
        /**
         * Returns the name.

         * @return String
         */
        val name: String,
        /**
         * DOCUMENT ME!

         * @return DOCUMENT ME!
         */
        val height: Int,
        /**
         * DOCUMENT ME!

         * @return DOCUMENT ME!
         */
        val width: Int) : Serializable {
    private val markers: Array<Array<TileType?>> = Array(height) { arrayOfNulls<TileType?>(width) }

    /**
     * DOCUMENT ME!

     * @param value
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    operator fun get(value: Vector2D): TileType? {
        return markers[value.x][value.y]
    }

    /**
     * DOCUMENT ME!

     * @param coordinate
     * *          DOCUMENT ME!
     * *
     * @param type
     * *          DOCUMENT ME!
     */
    operator fun set(coordinate: Vector2D, type: TileType) {
        markers[coordinate.x][coordinate.y] = type
    }

    /**
     * DOCUMENT ME!

     * @param value
     * *          DOCUMENT ME!
     */
    fun set(value: Tile?) {
        if (value != null) {
            set(value.location, value.tileType)
        }
    }

    companion object {

        private const val serialVersionUID = 3689631397870776625L
    }

}