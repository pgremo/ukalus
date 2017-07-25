package ukalus.container.creators

import junit.framework.TestCase
import ukalus.container.ObjectRegistry
import ukalus.container.Resolver
import ukalus.container.resolvers.ValueResolver
import java.net.URL

class ConstructorCreatorTest : TestCase() {

    private val registry = ObjectRegistry()

    fun test0ArgConstructor() {
        val creator = ConstructorCreator(Any::class.java, arrayOf<Resolver>())
        TestCase.assertNotNull(creator.newInstance())
    }

    fun test1ArgConstructor() {
        val creator = ConstructorCreator(Integer::class.java, arrayOf<Resolver>(ValueResolver(registry, "1")))
        val actual = creator.newInstance()
        TestCase.assertNotNull(actual)
        TestCase.assertEquals(1, actual)
    }

    fun test4ArgConstructorWithNonStrings() {
        val creator = ConstructorCreator(URL::class.java, arrayOf<Resolver>(ValueResolver(registry, "http"), ValueResolver(registry, "www.foo.com"), ValueResolver(registry, "80"), ValueResolver(registry, "/stuff")))
        val actual = creator.newInstance()
        TestCase.assertNotNull(actual)
        TestCase.assertEquals(URL("http://www.foo.com:80/stuff"), actual)
    }

}
