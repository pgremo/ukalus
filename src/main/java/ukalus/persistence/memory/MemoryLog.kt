package ukalus.persistence.memory

import ukalus.persistence.Log

import java.io.IOException
import java.util.LinkedList

class MemoryLog<T> : Log<T> {

    private val storage = LinkedList<T>()

    override fun clear() {
        storage.clear()
    }

    override fun add(o: T) {
        storage.add(o)
    }

    override fun close() {
    }

    override fun iterator(): Iterator<T> {
        return storage.iterator()
    }

}
