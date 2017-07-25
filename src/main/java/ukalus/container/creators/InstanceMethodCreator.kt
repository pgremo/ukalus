package ukalus.container.creators

import ukalus.container.InstanceCreator
import ukalus.container.Resolver
import java.lang.reflect.Modifier.PUBLIC
import java.lang.reflect.Modifier.STATIC

class InstanceMethodCreator(private val resolver: Resolver, private val name: String, private val arguments: Array<Resolver>) : InstanceCreator {
    override fun newInstance(): Any {
        val value = resolver.getValue(Any::class.java)!!
        val method = value.javaClass.methods.find { it.modifiers == PUBLIC && it.name == name && it.parameterCount == arguments.size }!!
        val types = method.parameterTypes
        val values = arguments.mapIndexed { index, resolver -> resolver.getValue(types[index]) }
        return method.invoke(value, *values.toTypedArray())
    }
}
