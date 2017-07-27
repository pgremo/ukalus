package ukalus.container.creators

import ukalus.container.InstanceCreator
import ukalus.container.Resolver

class ConstructorCreator(private val type: Class<*>, private val arguments: Array<Resolver>) : InstanceCreator {
    override fun newInstance(): Any {
        val constructor = type.constructors.find { it.parameterCount == arguments.size }!!
        val types = constructor.parameterTypes
        val values = arguments.mapIndexed { index, resolver -> resolver.getValue(types[index]) }
        return constructor.newInstance(*values.toTypedArray())
    }
}
