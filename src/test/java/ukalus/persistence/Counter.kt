package ukalus.persistence

import java.io.Serializable

internal class Counter : Serializable {
    private var total: Long = 0

    fun add(value: Long): Long {
        total += value
        return total
    }

    fun total(): Long {
        return total
    }
}