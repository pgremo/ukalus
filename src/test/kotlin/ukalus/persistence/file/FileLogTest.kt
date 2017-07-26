/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence.file

import junit.framework.TestCase
import ukalus.persistence.Log
import java.io.File
import java.util.function.Function

/**
 * @author gremopm
 */
class FileLogTest : TestCase() {

    private var file: File? = null

    /*
   * @see TestCase#setUp()
   */
    override fun setUp() {
        super.setUp()
        file = File("test.log")
    }

    /*
   * @see TestCase#tearDown()
   */
    override fun tearDown() {
        super.tearDown()
        file!!.delete()
    }

    fun testForeach() {
        var log: Log<Function<*, *>> = FileLog(file!!)
        val command1 = MockLogCommand("one")
        val command2 = MockLogCommand("two")
        log.add(command1)
        log.add(command2)
        log = FileLog<Function<*, *>>(file!!)
        val command3 = MockLogCommand("three")
        log.add(command3)
        assertEquals(listOf(command1, command2, command3), log.toList())
    }

    fun testClear() {
        val log = FileLog<Function<*, *>>(file!!)
        val command1 = MockLogCommand("one")
        val command2 = MockLogCommand("two")
        log.add(command1)
        log.add(command2)
        log.clear()
        assertEquals(0, log.toList().size)
    }
}