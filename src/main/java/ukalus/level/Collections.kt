package ukalus.level

import org.apache.commons.collections4.Bag
import java.util.*
import java.util.concurrent.ThreadLocalRandom

fun <T> Iterable<T>.separate(predicate: (T) -> Boolean): List<List<T>> {
    val all = mutableListOf<List<T>>()
    var open = mutableListOf<T>()
    this.forEach {
        if (predicate(it)) {
            if (!open.isEmpty()) {
                all.add(open)
                open = mutableListOf<T>()
            }
        } else {
            open.add(it)
        }
    }
    if (!open.isEmpty()) {
        all.add(open)
    }
    return all
}

fun <T> List<T>.random(random: Random = ThreadLocalRandom.current()): T? = if (this.isEmpty()) null else this[random.nextInt(this.size)]

fun <T> Bag<T>.random(random: Random = ThreadLocalRandom.current()): T? = this.drop(random.nextInt(this.size)).first()

fun <T> List<T>.shuffle(random: Random = ThreadLocalRandom.current()): List<T> = this.toList().apply { Collections.shuffle(this, random) }