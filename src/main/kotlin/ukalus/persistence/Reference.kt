/*
 * Created on May 13, 2004
 *
 */
package ukalus.persistence

/**
 * Generic reference object.

 * @author gremopm
 */
class Reference<T> {

    private var `object`: T? = null

    /**
     * Get the current value of the reference.

     * @return
     */
    fun get(): T? {
        return `object`
    }

    /**
     * Set an object to reference.

     * @param object
     */
    fun set(`object`: T?) {
        this.`object` = `object`
    }
}