package ukalus

import java.io.Serializable

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Door
/**
 * Creates a new Door object.

 * @param lock
 * *          DOCUMENT ME!
 * *
 * @param open
 * *          DOCUMENT ME!
 */
(
        /**
         * DOCUMENT ME!

         * @return DOCUMENT ME!
         */
        val lock: Lock?, open: Boolean) : Serializable {
    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    var isOpen: Boolean = false
        private set

    init {
        this.isOpen = open
    }

    /**
     * DOCUMENT ME!
     */
    fun open() {
        isOpen = lock == null || !lock.isLocked
    }

    /**
     * DOCUMENT ME!
     */
    fun close() {
        isOpen = false
    }
}