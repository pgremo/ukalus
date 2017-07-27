package ukalus.aggregate

interface EventStore {
    fun append(event: Any)
    fun load(): Sequence<Any>
}
