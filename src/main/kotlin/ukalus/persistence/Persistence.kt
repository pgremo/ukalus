package ukalus.persistence

import ukalus.persistence.file.FileLog
import ukalus.persistence.file.FileStore
import java.io.File
import java.io.FilenameFilter
import java.io.Serializable
import java.util.*
import java.util.function.Function

object Persistence {

    lateinit private var dataFile: File
    lateinit private var logFile: File
    lateinit private var store: Store<HashMap<Any, Any>>
    lateinit private var log: Log<Function<Reference<HashMap<Any, Any>?>, Any?>>
    private var persistence: Engine<HashMap<Any, Any>>? = null

    @Throws(PersistenceException::class)
    fun create(name: String) {
        val directory = File(System.getProperty("user.home"), "ukalus")
        directory.mkdirs()

        val files = directory.listFiles(Filter(name))
        if (files.isNotEmpty()) {
            throw PersistenceException(String.format("repository %s already exists", name))
        }

        dataFile = File(directory, String.format("%s.dat", name))
        logFile = File(directory, String.format("%s.log", name))
        store = FileStore<HashMap<Any, Any>>(dataFile)
        log = FileLog<Function<Reference<HashMap<Any, Any>?>, Any?>>(logFile)
        persistence = Engine(store, log)
        persistence!!.update(Create())
        persistence!!.checkpoint()

    }

    @Throws(PersistenceException::class)
    fun open(name: String) {
        val directory = File(System.getProperty("user.home"), "ukalus")

        val files = directory.listFiles(Filter(name))
        if (files.isNotEmpty()) {
            throw PersistenceException(String.format("repository %s does not exist", name))
        }

        dataFile = File(directory, String.format("%s.dat", name))
        logFile = File(directory, String.format("%s.log", name))
        store = FileStore<HashMap<Any, Any>>(dataFile)
        log = FileLog<Function<Reference<HashMap<Any, Any>?>, Any?>>(logFile)
        persistence = Engine(store, log)

    }

    @Throws(PersistenceException::class)
    fun put(key: Any, value: Any) {
        persistence!!.update(Put(key, value))
        persistence!!.checkpoint()
    }

    @Throws(PersistenceException::class)
    operator fun get(key: Any): Any? {
        return persistence!!.query(Get(key))
    }

    fun close() {}

    fun delete(name: String) {
        persistence!!.close()

        val directory = File(System.getProperty("user.home"), "ukalus")
        val files = directory.listFiles(Filter(name))

        for (file in files!!) {
            file.delete()
        }
    }

    fun list(): Array<String>? {
        return null
    }

    private class Filter(private val name: String) : FilenameFilter {

        override fun accept(dir: File, target: String): Boolean {
            return target.startsWith(name + ".")
        }
    }

    private class Create : Function<Reference<HashMap<Any, Any>?>, Any?>, Serializable {

        override fun apply(reference: Reference<HashMap<Any, Any>?>): Any? {
            reference.set(HashMap<Any, Any>())
            return null
        }
    }

    private class Get(private val key: Any) : Function<Reference<HashMap<Any, Any>?>, Any?>, Serializable {

        override fun apply(reference: Reference<HashMap<Any, Any>?>): Any? {
            return reference.get()?.get(key)
        }
    }

    private class Put(private val key: Any, private val value: Any) : Function<Reference<HashMap<Any, Any>?>, Any?>, Serializable {

        override fun apply(reference: Reference<HashMap<Any, Any>?>): Any? {
            return reference.get()?.put(key, value)
        }
    }

}