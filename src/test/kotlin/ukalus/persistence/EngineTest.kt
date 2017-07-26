package ukalus.persistence

import junit.framework.TestCase
import ukalus.persistence.file.FileLog
import ukalus.persistence.file.FileStore
import java.io.File

class EngineTest : TestCase() {

    private var server: Engine<Counter>? = null
    private val servers = mutableListOf<Engine<out Any>>()

    fun testServer() {
        crashRecover()
        set()
        add(40, 40) // 1
        add(30, 70) // 2
        assertTotal(70)

        crashRecover()
        assertTotal(70)

        add(20, 90) // 3
        add(15, 105) // 4
        server!!.checkpoint()
        server!!.checkpoint()
        assertTotal(105)
        throwException()

        crashRecover()
        server!!.checkpoint()
        add(10, 115) // 5
        server!!.checkpoint()
        add(5, 120) // 6
        add(4, 124) // 7
        assertTotal(124)

        crashRecover()
        add(3, 127) // 8
        assertTotal(127)

        server!!.checkpoint()
        clearPersistence()
        server!!.checkpoint()
        crashRecover()
        assertTotal(127)

        add(2, 129) // 9
        crashRecover()
        assertTotal(129)

    }

    private fun set() {
        server!!.update(Setter(Counter()))
    }

    private fun crashRecover() {
        server = Engine(FileStore(File("test")), FileLog(File("test.log")))
        servers.add(server!!)
    }

    private fun throwException() {
        try {
            server!!.update(ThrowException())
        } catch(e: Exception) {

        }
    }

    private fun add(value: Long, expected: Long) {
        val total = server!!.update(Adder(value)) as Long?
        TestCase.assertEquals(expected, total!!.toLong())
    }

    private fun assertTotal(expected: Long) {
        val system = server!!.update(Getter<Counter?>()) as Counter?
        TestCase.assertEquals(expected, system!!.total())
    }

    override fun tearDown() {
        super.tearDown()
        clearPersistence()
    }

    private fun clearPersistence() {
        val iterator = servers.iterator()
        while (iterator.hasNext()) {
            iterator.next()
                    .checkpoint()
            iterator.remove()
        }

        val store = File("test")
        if (store.exists()) {
            store.delete()
        }
        val log = File("test.log")
        if (log.exists()) {
            log.delete()
        }
    }
}