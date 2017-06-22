/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence

import java.io.IOException
import java.io.Serializable

/**
 * Encapsulation of the storing and loading of an object.

 * @author gremopm
 */
interface Store<T> {

    /**
     * Store the given object (eg, serialization).

     * @param object
     * *
     * @throws Exception
     */
    @Throws(IOException::class)
    fun store(`object`: T?)

    /**
     * Load the previously stored object.

     * @return the previously stored object.
     * *
     * @throws Exception
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    fun load(): T?

    /**
     * Close this Store.

     * @throws IOException
     */
    @Throws(IOException::class)
    fun close()
}