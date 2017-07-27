package ukalus

import ukalus.math.Vector2D

import java.io.Serializable

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Stairs
/**
 * Constructor for Stairs.

 * @param direction
 */
(
        /**
         * Returns the direction.

         * @return String
         */
        val direction: String) : Portal, Serializable {
    /**
     * Returns the coordinate.

     * @return Coordinate
     */
    /**
     * Sets the coordinate.

     * @param location
     * *          The coordinate to set
     */
    var coordinate: Vector2D? = null
    /**
     * Returns the levelName.

     * @return String
     */
    /**
     * Sets the levelName.

     * @param levelName
     * *          The levelName to set
     */
    var levelName: String? = null

    companion object {

        private const val serialVersionUID = 3833188025416037685L
        val UP = "up"
        val DOWN = "down"
    }
}