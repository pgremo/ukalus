/*
 * Created on Mar 20, 2005
 *
 */
package ukalus.container.resolvers

import ukalus.container.ObjectRegistry
import ukalus.container.Resolver

class ValueResolver(private val registry: ObjectRegistry, private val value: Any) : Resolver {
    override fun getValue(type: Class<*>): Any {
        var result = value
        if (!type.isAssignableFrom(value.javaClass)) {
            val converter = registry.getConverter(type) ?: throw IllegalArgumentException("converter not found for $type")
            result = converter.invoke(value)
        }
        return result
    }
}
