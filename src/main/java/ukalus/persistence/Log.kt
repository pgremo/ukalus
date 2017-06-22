/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence

import java.io.Serializable
import java.util.function.Function

import java.io.IOException

/**
 * Maintain an ordered list of objects, with the ability to execute a command
 * over all of them.

 * @author gremopm
 * *
 * @see Function
 */
interface Log<T> : Iterable<T> {

    /**
     * Remove all objects that have been added to this Log.

     * @throws Exception
     */
    @Throws(IOException::class)
    fun clear()

    /**
     * Add the given object to this Log.

     * @param object
     * *          to be added to this Log
     * *
     * @throws Exception
     */
    @Throws(IOException::class)
    fun add(o: T)

    /**
     * Close this Log.

     * @throws Exception
     */
    @Throws(IOException::class)
    fun close()
}