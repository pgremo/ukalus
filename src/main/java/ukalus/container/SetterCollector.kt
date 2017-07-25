package ukalus.container

import java.lang.reflect.Method
import java.util.function.Consumer

class SetterCollector(private val setters: MutableMap<String, Method>) : Consumer<Method> {

    override fun accept(method: Method) {
        val name = method.name
        if (name.startsWith("set") && method.parameterTypes.size == 1) {
            setters.put(Character.toLowerCase(name[3]) + name.substring(4), method)
        }
    }
}
