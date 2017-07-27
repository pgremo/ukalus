package ukalus

import java.io.Serializable

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
open class Thing : Serializable {
    /**
     * Returns the owner.

     * @return Creature
     */
    /**
     * Sets the owner.

     * @param owner
     * *          The owner to set
     */
    var owner: Creature? = null

    companion object {

        private const val serialVersionUID = 3257288011124519993L
    }
}