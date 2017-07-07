/*
 * Created on May 18, 2004
 *  
 */
package ukalus.persistence

import java.io.Serializable
import java.util.function.Function

/**
 * @author gremopm
 */
class Getter<T> : Function<Reference<T>, Any?>, Serializable {
    override fun apply(`object`: Reference<T>): Any? {
        return `object`.get()
    }
}