package ukalus.container

import junit.framework.TestCase
import java.lang.reflect.Method
import java.util.*

class SetterCollectorTest : TestCase() {

    fun testCollectionSetters() {
        val expected = HashMap<String, Method>()
        expected.put("x", TestClass::class.java.getMethod("setX", Any::class.java))
        expected.put("someThingElse", TestClass::class.java.getMethod("setSomeThingElse", Any::class.java))
        val actual = TestClass::class.java.methods
                .filter { it.name.startsWith("set") && it.parameterCount == 1 }
                .associateBy { Character.toLowerCase(it.name[3]) + it.name.substring(4) }
        TestCase.assertEquals(expected, actual)
    }

    internal inner class TestClass {

        fun setX(value: Any) {

        }

        fun setSomeThingElse(value: Any) {

        }

        fun setInvalid(value: Any, value2: Any) {

        }

        fun nonSetter(value: Any) {

        }
    }

}
