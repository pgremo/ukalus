package ukalus.persistence

import java.io.Serializable
import java.util.function.Function

internal class Adder(private val value: Long) : Function<Reference<Counter?>, Any?>, Serializable {
    override fun apply(`object`: Reference<Counter?>): Any? {
        return (`object`.get() as Counter).add(value)
    }
}