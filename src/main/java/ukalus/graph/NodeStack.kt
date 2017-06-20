package ukalus.graph

import java.util.LinkedList

class NodeStack : NodeCollection {

    private val storage = LinkedList<Node>()

    override fun add(e: Node) {
        storage.addFirst(e)
    }

    override fun get(): Node = storage.peek()

    override fun remove() {
        storage.poll()
    }

    override val isEmpty: Boolean
        get() = storage.isEmpty()

    override fun toString(): String = storage.toString()
}
