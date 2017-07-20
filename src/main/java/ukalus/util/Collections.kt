package ukalus.util

import org.apache.commons.collections4.Bag
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.coroutines.experimental.buildSequence

fun <T> Iterable<T>.cluster(predicate: (T) -> Boolean): Sequence<List<T>> {
    val source = this
    return buildSequence {
        var open = mutableListOf<T>()
        source.forEach {
            if (!predicate(it)) {
                if (!open.isEmpty()) {
                    yield(open)
                    open = mutableListOf<T>()
                }
            }
            open.add(it)
        }
        if (!open.isEmpty()) {
            yield(open)
        }
    }
}

fun <T> Iterable<T>.sift(predicate: (T) -> Boolean): Sequence<List<T>> {
    val source = this
    return buildSequence {
        var open = mutableListOf<T>()
        source.forEach {
            if (predicate(it)) {
                if (!open.isEmpty()) {
                    yield(open)
                    open = mutableListOf<T>()
                }
            } else {
                open.add(it)
            }
        }
        if (!open.isEmpty()) {
            yield(open)
        }
    }
}

fun <T> Array<T>.random(random: Random = ThreadLocalRandom.current()): T? = if (this.isEmpty()) null else this[random.nextInt(this.size)]

fun <T> List<T>.random(random: Random = ThreadLocalRandom.current()): T? = if (this.isEmpty()) null else this[random.nextInt(this.size)]

fun <T> Bag<T>.random(random: Random = ThreadLocalRandom.current()): T? = this.drop(random.nextInt(this.size)).first()

fun <T> List<T>.shuffle(random: Random = ThreadLocalRandom.current()): List<T> = this.toList().apply { Collections.shuffle(this, random) }

fun <T> List<T>.takeRandom(random: Random = ThreadLocalRandom.current()): List<T> = this.take(random.nextInt(1, this.size + 1))