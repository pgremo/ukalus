/*
 * Created on May 18, 2004
 *  
 */
package ukalus.persistence

import java.util.function.Function

import java.io.Serializable

/**
 * @author gremopm
 */
class Setter<T>(private var value: T) : Function<Reference<T>, Any?>, Serializable {
    override fun apply(`object`: Reference<T>): Any? {
        `object`.set(value)
        return value
    }
}