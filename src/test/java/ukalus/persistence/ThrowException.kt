/*
 * Created on Dec 28, 2004
 *
 */
package ukalus.persistence

import java.io.Serializable
import java.util.function.Function

/**
 * @author pmgremo
 */
class ThrowException<T> : Function<Reference<T?>, Any?>, Serializable {
    override fun apply(`object`: Reference<T?>): Any? {
        throw RuntimeException()
    }
}