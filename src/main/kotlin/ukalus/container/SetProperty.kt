package ukalus.container

import java.lang.reflect.Method

fun setProperty(setters: Map<String, Method>, input: Any): (Map.Entry<String, Resolver>) -> Unit {
    return { property: Map.Entry<String, Resolver> ->
        setters[property.key]!!.apply {
            invoke(input, property.value.getValue(parameterTypes[0]))
        }
    }
}
