package ukalus.ui.jcurses

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Marker private constructor(
        /**
         * Returns the name.

         * @return String
         */
        val name: String) {

    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    override fun toString(): String {
        return name
    }

    companion object {

        val WALL = Marker("wall")
        val FLOOR = Marker("passage")
        val DOOR_OPEN = Marker("door_open")
        val DOOR_CLOSED = Marker("door_closed")
        val STAIRS_UP = Marker("stairs_up")
        val STAIRS_DOWN = Marker("stairs_down")
        val WEAPON = Marker("weapon")
        val HERO = Marker("hero")
    }
}