package ukalus.container

import java.lang.reflect.Method
import java.util.HashMap
import java.util.stream.Stream

class PropertySetter(private val properties: Map<String, Resolver>) {

    fun setProperties(input: Any) {
        val methods = input.javaClass.methods
        val setters = HashMap<String, Method>(methods.size)
        Stream.of(*methods).forEach(SetterCollector(setters))
        properties.entries.forEach(setProperty(setters, input))
    }
}
