package ukalus.container.resolvers

import ukalus.container.ObjectRegistry
import ukalus.container.Resolver

class ReferenceResolver(private val id: String, private val registry: ObjectRegistry) : Resolver {
    override fun getValue(type: Class<*>): Any? {
        val result = registry.getObject(id)
        if (!type.isAssignableFrom(result?.javaClass)) {
            throw IllegalArgumentException("$id is not assignable to $type")
        }
        return result
    }
}
