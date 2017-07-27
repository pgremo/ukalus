package ukalus.container

class ObjectFactory(private val creator: InstanceCreator, private val setter: PropertySetter) {
    val instance: Any by lazy {
        creator.newInstance().apply {
            setter.setProperties(this)
        }
    }
}
