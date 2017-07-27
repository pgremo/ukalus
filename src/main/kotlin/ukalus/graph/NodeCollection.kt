package ukalus.graph

interface NodeCollection {

    fun add(e: Node)

    fun get(): Node?

    fun remove()

    val isEmpty: Boolean

}
