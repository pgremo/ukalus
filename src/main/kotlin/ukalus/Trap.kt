package ukalus

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Trap {

    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    var isSet: Boolean = false
        private set

    init {
        isSet = true
    }

    /**
     * DOCUMENT ME!
     */
    fun activate() {
        deactivate()
    }

    /**
     * DOCUMENT ME!
     */
    fun deactivate() {
        isSet = false
    }
}