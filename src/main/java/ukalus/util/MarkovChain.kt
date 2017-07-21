package ukalus.util

import org.apache.commons.collections4.Bag
import org.apache.commons.collections4.bag.HashBag
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class MarkovChain<E : Any> {

    private val items = HashMap<E?, Bag<E>>()

    fun addAll(items: Iterable<E>) {
        add(items.fold<E?, E?>(null, { acc, i ->
            add(acc, i)
            i
        }), null)
    }

    fun add(current: E?, next: E?) {
        items.computeIfAbsent(current) { HashBag<E>() }.add(next)
    }

    fun randomWalk(random: Random = ThreadLocalRandom.current()): Sequence<E> {
        fun nextFunction(x: E? = null): E? = items[x]?.random(random)
        return generateSequence(nextFunction(), ::nextFunction)
    }

    override fun toString() = items.toString()
}