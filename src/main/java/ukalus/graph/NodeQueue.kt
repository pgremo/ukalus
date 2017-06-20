package ukalus.graph

import java.util.*

class NodeQueue : NodeCollection {

    private val storage = LinkedList<Node>()

    override fun add(e: Node) = storage.addLast(e)

    override fun get(): Node = storage.peek()

    override fun remove() {
        storage.poll()
    }

    override val isEmpty: Boolean
        get() = storage.isEmpty()

    override fun toString(): String = storage.toString()
}
