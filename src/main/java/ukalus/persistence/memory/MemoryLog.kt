package ukalus.persistence.memory

import ukalus.persistence.Log

import java.io.IOException
import java.util.LinkedList

class MemoryLog<T> : Log<T> {

    private val storage = LinkedList<T>()

    @Throws(IOException::class)
    override fun clear() {
        storage.clear()
    }

    @Throws(IOException::class)
    override fun add(o: T) {
        storage.add(o)
    }

    @Throws(IOException::class)
    override fun close() {
    }

    override fun iterator(): Iterator<T> {
        return storage.iterator()
    }

}
