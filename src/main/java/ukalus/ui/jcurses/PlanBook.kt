package ukalus.ui.jcurses

import ukalus.persistence.Persistence
import ukalus.persistence.PersistenceException

import java.io.Serializable

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class PlanBook : Serializable {
    private var name: String? = null
    private var page: Any? = null

    /**
     * DOCUMENT ME!

     * @param key
     * *          DOCUMENT ME!
     */
    fun turnTo(key: String) {
        if (name != null && name != key) {
            try {
                Persistence.put(name!!, page!!)
            } catch (e: PersistenceException) {
                e.printStackTrace()
            }

        }

        name = key

        try {
            page = Persistence[name!!]
        } catch (e: PersistenceException) {
            e.printStackTrace()
        }

    }

    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    fun get(): Any? {
        return page
    }

    /**
     * DOCUMENT ME!

     * @param key
     * *          DOCUMENT ME!
     * *
     * @param page
     * *          DOCUMENT ME!
     */
    fun add(key: String, page: Any) {
        turnTo(key)
        this.page = page
    }

    companion object {

        private const val serialVersionUID = 3690195434467308848L
    }
}