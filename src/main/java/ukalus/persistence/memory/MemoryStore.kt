package ukalus.persistence.memory

import ukalus.persistence.Reference
import ukalus.persistence.Store

import java.io.IOException
import java.io.Serializable

class MemoryStore<T : Serializable> : Store<T> {

    private val reference = Reference<T>()

    @Throws(IOException::class)
    override fun store(`object`: T?) {
        reference.set(`object`)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    override fun load(): T? {
        return reference.get()
    }

    @Throws(IOException::class)
    override fun close() {
    }

}
