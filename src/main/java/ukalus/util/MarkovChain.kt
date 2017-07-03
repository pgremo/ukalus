package ukalus.util

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.bag.HashBag
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class MarkovChain<E : Any> {

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

    fun randomWalk(random: Random = ThreadLocalRandom.current()): List<E> {
        fun nextFunction(x: E? = null): E? = items[x]?.random(random)
        return generateSequence(nextFunction(), ::nextFunction).toList()
    }

    override fun toString() = items.toString()
}