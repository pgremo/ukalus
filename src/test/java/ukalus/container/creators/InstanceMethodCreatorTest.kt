package ukalus.container.creators

import junit.framework.TestCase
import junit.framework.TestCase.*
import ukalus.container.ObjectRegistry
import ukalus.container.Resolver
import ukalus.container.resolvers.ValueResolver
import java.util.*

class InstanceMethodCreatorTest : TestCase() {

    private var arg0Called: Boolean = false

    fun createInstance0Arg(): Any {
        arg0Called = true
        return this
    }

    fun test0ArgCreator() {
        val creator = InstanceMethodCreator(ValueResolver(ObjectRegistry(), this), "createInstance0Arg", arrayOf<Resolver>())
        val actual = creator.newInstance()
        assertEquals(this, actual)
        assertTrue(arg0Called)
    }

    private var arg1Called: Boolean = false
    private var arg1Args: Array<Any>? = null

    fun createInstance1Arg(value: String): Any {
        arg1Called = true
        arg1Args = arrayOf<Any>(value)
        return this
    }

    fun test1ArgCreator() {
        val registry = ObjectRegistry()
        val creator = InstanceMethodCreator(ValueResolver(registry, this), "createInstance1Arg", arrayOf<Resolver>(ValueResolver(registry, "something")))
        val actual = creator.newInstance()
        assertEquals(this, actual)
        assertTrue(arg1Called)
        assertTrue(Arrays.equals(arrayOf<Any>("something"), arg1Args))
    }

    private var arg2Called: Boolean = false
    private var arg2Args: Array<Any>? = null

    fun createInstance2Arg(value: Int): Any {
        arg2Called = true
        arg2Args = arrayOf<Any>(value)
        return this
    }

    fun test1ArgIntCreator() {
        val registry = ObjectRegistry()
        val creator = InstanceMethodCreator(ValueResolver(registry, this), "createInstance2Arg", arrayOf<Resolver>(ValueResolver(registry, "1")))
        val actual = creator.newInstance()
        assertEquals(this, actual)
        assertTrue(arg2Called)
        assertTrue(Arrays.equals(arrayOf<Any>(1), arg2Args))
    }

}
