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

fun <T> Array<T>.random(random: Random = ThreadLocalRandom.current()): T? = if (isEmpty()) null else this[random.nextInt(size)]

fun <T> List<T>.random(random: Random = ThreadLocalRandom.current()): T? = if (isEmpty()) null else this[random.nextInt(size)]

fun <T> Bag<T>.random(random: Random = ThreadLocalRandom.current()): T? = drop(random.nextInt(size)).first()

fun <T> List<T>.shuffle(random: Random = ThreadLocalRandom.current()): List<T> = toList().apply { Collections.shuffle(this, random) }

fun <T> List<T>.takeRandom(random: Random = ThreadLocalRandom.current()): List<T> = take(random.nextInt(1, size + 1))