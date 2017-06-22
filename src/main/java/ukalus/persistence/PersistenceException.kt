package ukalus.persistence

import java.io.IOException

/**
 * @author pmgremo
 */
class PersistenceException
/**
 * Constructor for PersistenceException.

 * @param s
 */(s: String?) : IOException(s) {

    companion object {

        private val serialVersionUID = 3618981191634858544L
    }

}