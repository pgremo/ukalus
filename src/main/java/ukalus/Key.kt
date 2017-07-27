package ukalus

import java.util.*

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Key private constructor(
        /**
         * DOCUMENT ME!

         * @return DOCUMENT ME!
         */
        val id: Int) {

    /**
     * DOCUMENT ME!

     * @param other
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    override fun equals(other: Any?): Boolean {
        val key = other as Key?

        return key != null && id == key.id
    }

    override fun hashCode(): Int {
        return id
    }

    companion object {

        private val MAX_KEYS = 20
        private var random: Random? = null
        private var keys: Array<Key>? = null

        init {
            random = Random()
            keys = Array(MAX_KEYS) { Key(it) }
        }

        /**
         * DOCUMENT ME!

         * @return DOCUMENT ME!
         */
        fun random(): Key {
            return keys!![random!!.nextInt(keys!!.size)]
        }
    }
}