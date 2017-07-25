package ukalus.container

import java.lang.reflect.InvocationTargetException

interface InstanceCreator {

    @Throws(IllegalArgumentException::class, InstantiationException::class, IllegalAccessException::class, InvocationTargetException::class)
    fun newInstance(): Any

}