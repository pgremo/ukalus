package ukalus.util

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.bag.HashBag
import ukalus.level.random
import java.util.*

class MarkovChain<E>(private val random: Random) : Iterable<E?> {

    private val items = HashMap<E?, Bag<E>>()

    fun addAll(items: Iterable<E>) {
        var key: E? = null
        for (item in items) {
            add(key, item)
            key = item
        }
        add(key, null)
    }

    fun add(current: E?, next: E?) {
        items.computeIfAbsent(current) { HashBag<E>() }.add(next)
    }

    override fun iterator(): Iterator<E?> {
        return object : Iterator<E?> {
            private var next: E? = null

            init {
                setNext()
            }

            override fun hasNext(): Boolean {
                return next != null
            }

            override fun next(): E? {
                val result = next
                setNext()
                return result
            }

            private fun setNext() {
                next = items[next]?.random(random)
            }
        }
    }

    override fun toString(): String {
        return items.map { (key, list) -> "[$key]: (${list.size}) $list" }.joinToString("\n")
    }
}