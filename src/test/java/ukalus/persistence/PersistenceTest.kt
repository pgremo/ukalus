package ukalus.persistence

import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.assertEquals

/**
 * @author pmgremo
 */
class PersistenceTest {

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Persistence.create("test")
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        Persistence.close()
        Persistence.delete("test")
    }

    @Test
    @Throws(Exception::class)
    fun testOperations() {
        val key = "key"
        val value = "value"
        Persistence.put(key, value)
        assertEquals(value, Persistence[key])
    }

}