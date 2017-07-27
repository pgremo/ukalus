package ukalus

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Lock
/**
 * Creates a new Lock object.

 * @param key
 * *          DOCUMENT ME!
 */
(private val key: Key) {
    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    var isLocked: Boolean = false
        private set

    /**
     * DOCUMENT ME!

     * @param key
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    fun unlock(key: Key): Boolean {
        isLocked = this.key != key

        return !isLocked
    }

    /**
     * DOCUMENT ME!

     * @param key
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    fun lock(key: Key): Boolean {
        isLocked = this.key == key

        return isLocked
    }
}