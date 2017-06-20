package ukalus.graph

import java.util.*

class NodeRandom(private val random: Random) : NodeCollection {
    private val storage = ArrayList<Node>()

    override fun add(e: Node) {
        if (storage.isEmpty()) {
            storage.add(e)
        } else {
            storage.add(random.nextInt(storage.size), e)
        }
    }

    override fun get(): Node? = if (!storage.isEmpty()) {
        storage[0]
    } else {
        null
    }

    override fun remove() {
        if (!storage.isEmpty()) {
            storage.removeAt(0)
        }
    }

    override val isEmpty: Boolean
        get() = storage.isEmpty()

    override fun toString(): String = storage.toString()
}
