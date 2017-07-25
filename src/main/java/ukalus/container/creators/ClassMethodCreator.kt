package ukalus.container.creators

import ukalus.container.InstanceCreator
import ukalus.container.Resolver
import java.lang.reflect.Modifier.PUBLIC
import java.lang.reflect.Modifier.STATIC

class ClassMethodCreator(private val type: Class<*>, private val name: String, private val arguments: Array<Resolver>) : InstanceCreator {
    override fun newInstance(): Any {
        val method = type.methods.find { it.modifiers == PUBLIC + STATIC && it.name == name && it.parameterCount == arguments.size }!!
        val types = method.parameterTypes
        val values = arguments.mapIndexed { index, resolver -> resolver.getValue(types[index]) }
        return method.invoke(type, *values.toTypedArray())
    }
}
