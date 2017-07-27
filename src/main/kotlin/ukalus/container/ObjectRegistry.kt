/*
 * Created on Jan 25, 2005
 *
 */
package ukalus.container

import java.io.File

/**
 * @author gremopm
 */
class ObjectRegistry {

    private val converters = mutableMapOf<Class<*>, (Any) -> Any>(
            Boolean::class.javaPrimitiveType!! to { it -> (it as String).toBoolean() },
            Byte::class.javaPrimitiveType!! to { it -> (it as String).toByte() },
            Character::class.javaPrimitiveType!! to { it -> (it as String).toInt().toChar() },
            (Class::class.java as Class<*>) to { it -> Class.forName(it as String) },
            Double::class.javaPrimitiveType!! to { it -> (it as String).toDouble() },
            (File::class.java as Class<*>) to { it -> File(it as String) },
            Float::class.javaPrimitiveType!! to { it -> (it as String).toFloat() },
            Int::class.javaPrimitiveType!! to { it -> (it as String).toInt() },
            Long::class.javaPrimitiveType!! to { it -> (it as String).toLong() },
            (Any::class.java as Class<*>) to Any::toString,
            Short::class.javaPrimitiveType!! to { it -> (it as String).toShort() },
            (String::class.java as Class<*>) to { it -> it as String }
    )
    private val factories = mutableMapOf<String, ObjectFactory>()

    fun addConverter(type: Class<*>, converter: (Any) -> Any) {
        converters[type] = converter
    }

    fun getConverter(type: Class<*>): ((Any) -> Any)? = converters[type]

    fun addObject(id: String, factory: ObjectFactory) {
        factories[id] = factory
    }

    fun getObject(id: String): Any? = factories[id]?.instance

}