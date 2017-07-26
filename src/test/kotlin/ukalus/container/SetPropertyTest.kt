package ukalus.container

import junit.framework.TestCase
import ukalus.container.resolvers.ValueResolver
import java.lang.reflect.Method

class SetPropertyTest : TestCase() {

    private var stringValue: String? = null
    private var intValue: Int = 0

    override fun setUp() {
        super.setUp()
        stringValue = null
        intValue = 0
    }

    override fun tearDown() {
        super.tearDown()
        stringValue = null
        intValue = 0
    }

    fun setValue(value: String) {
        this.stringValue = value
    }

    fun setValue(value: Int) {
        this.intValue = value
    }

    fun testInvokeString() {
        val expected = "some stuff"
        val setters = mapOf<String, Method>("value" to javaClass.getMethod("setValue", String::class.java))
        val setter = setProperty(setters, this)
        setter(Entry("value", ValueResolver(ObjectRegistry(), expected)))
        TestCase.assertEquals(expected, stringValue)
    }

    fun testInvokeInt() {
        val expected = 33
        val setters = mapOf<String, Method>("value" to javaClass.getMethod("setValue", Integer.TYPE))
        val setter = setProperty(setters, this)
        setter(Entry("value", ValueResolver(ObjectRegistry(), "33")))
        TestCase.assertEquals(expected, intValue)
    }

    private data class Entry(override val key: String, override var value: Resolver) : Map.Entry<String, Resolver>

}
