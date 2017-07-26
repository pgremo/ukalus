/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence.file

import junit.framework.TestCase
import ukalus.persistence.Store

import java.io.File
import java.io.Serializable

/**
 * @author gremopm
 */
class FileStoreTest : TestCase() {

    private var file: File? = null

    /*
   * @see TestCase#setUp()
   */
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        file = File("test")
    }

    /*
   * @see TestCase#tearDown()
   */
    @Throws(Exception::class)
    override fun tearDown() {
        super.tearDown()
        file!!.delete()
    }

    @Throws(Exception::class)
    fun testOperations() {
        val original = "This is a test"
        val storage = FileStore<Serializable>(file!!)
        storage.store(original)
        val result = storage.load()
        TestCase.assertEquals(original, result)
    }
}