package ukalus.container

import java.lang.reflect.Method

class SetProperty(private val setters: Map<String, Method>, private val input: Any) : (Map.Entry<String, Resolver>) -> Unit {
    override fun invoke(property: Map.Entry<String, Resolver>) {
        val method = setters[property.key]
        method?.invoke(input, property.value.getValue(method.parameterTypes[0]))
    }
}
