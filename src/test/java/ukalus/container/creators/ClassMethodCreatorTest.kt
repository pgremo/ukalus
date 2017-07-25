package ukalus.container.creators

import junit.framework.TestCase
import ukalus.container.ObjectRegistry
import ukalus.container.Resolver
import ukalus.container.resolvers.ValueResolver
import java.lang.reflect.InvocationTargetException
import java.net.InetAddress
import java.net.UnknownHostException
import java.nio.ByteBuffer

class ClassMethodCreatorTest : TestCase() {

    @Throws(IllegalArgumentException::class, InstantiationException::class, IllegalAccessException::class, InvocationTargetException::class, UnknownHostException::class)
    fun test0ArgMethod() {
        val expected = InetAddress.getLocalHost()
        val creator = ClassMethodCreator(InetAddress::class.java,
                "getLocalHost", arrayOf<Resolver>())
        val actual = creator.newInstance()
        TestCase.assertEquals(expected, actual)
    }

    @Throws(IllegalArgumentException::class, InstantiationException::class, IllegalAccessException::class, InvocationTargetException::class, UnknownHostException::class)
    fun test1ArgMethod() {
        val expected = InetAddress.getByName("www.google.com")
        val creator = ClassMethodCreator(InetAddress::class.java,
                "getByName", arrayOf<Resolver>(ValueResolver(ObjectRegistry(),
                "www.google.com")))
        val actual = creator.newInstance()
        TestCase.assertEquals(expected, actual)
    }

    @Throws(IllegalArgumentException::class, InstantiationException::class, IllegalAccessException::class, InvocationTargetException::class, UnknownHostException::class)
    fun test1IntArgMethod() {
        val expected = ByteBuffer.allocate(1024)
        val creator = ClassMethodCreator(ByteBuffer::class.java,
                "allocate",
                arrayOf<Resolver>(ValueResolver(ObjectRegistry(), "1024")))
        val actual = creator.newInstance() as ByteBuffer
        TestCase.assertEquals(expected.capacity(), actual.capacity())
    }

}
