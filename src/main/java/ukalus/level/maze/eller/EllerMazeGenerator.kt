package ukalus.level.maze.eller

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.level.IntLevel
import ukalus.level.RegionFactory
import ukalus.level.maze.MazeRegion
import ukalus.math.Vector2D
import ukalus.util.cluster
import ukalus.util.compareToAndSwap
import ukalus.util.shuffle
import ukalus.util.takeRandom
import java.util.*
import kotlin.coroutines.experimental.buildSequence

class EllerMazeGenerator(private val random: Random,
                         private val height: Int,
                         private val width: Int
) : RegionFactory<Int> {

    override fun create(): MazeRegion {
        var nextSet = 0
        var next = IntArray(width - 2) { if (it % 2 == 1) wall else --nextSet }
        var current = next.copyOf()

        val maze: Array<IntArray> = Array(height) { IntArray(width) { wall } }

        buildSequence {

            for (q in 0 until (height - 1) / 2 - 1) {

                join(current)
                cut(current, next)

                (next.indices step 2)
                        .filter { next[it] == unknown }
                        .forEach { next[it] = --nextSet }

                yield(listOf(current, next))

                current = next.copyOf()
                next = IntArray(width - 2) { if (it % 2 == 1) wall else unknown }

            }

            join(current) { true }

        }.forEachIndexed { q: Int, (first, second) ->
            for (k in first.indices) {
                if (first[k] == wall) {
                    maze[2 * q + 1][k + 1] = wall
                    maze[2 * q + 2][k + 1] = wall
                } else {
                    maze[2 * q + 1][k + 1] = path
                    if (first[k] == second[k]) maze[2 * q + 2][k + 1] = path
                }
            }
        }

        for (k in next.indices) {
            maze[maze.size - 2][k + 1] = if (current[k] == wall) wall else path
        }

        return MazeRegion(maze)
    }

    private fun join(items: IntArray, combine: () -> Boolean = random::nextBoolean) {
        for (i in 1 until items.size step 2) {
            if (items[i] == wall && items[i - 1] != items[i + 1] && combine()) {
                val (previous, next) = items[i - 1].compareToAndSwap(items[i + 1])
                items[i] = previous
                items.indices
                        .filter { items[it] == previous }
                        .forEach { items[it] = next }
            }
        }
    }

    private fun cut(current: IntArray, next: IntArray) {
        (current.indices step 2)
                .cluster { it == 0 || current[it - 2] == current[it] }
                .map { it.shuffle(random) }
                .map { it.takeRandom(random) }
                .flatMap { it.asSequence() }
                .forEach { next[it] = current[it] }
    }

    companion object {
        private val unknown = 1
        private val wall = 0
        private val path = 1

        @JvmStatic fun main(args: Array<String>) {
            val generator = EllerMazeGenerator(RandomAdaptor(MersenneTwister()), 19, 79)

            val level = IntLevel(Array(19) { Array(79) { 0 } })
            val region = generator.create()
            region.place(Vector2D(0, 0), level)

            println(level)
        }
    }
}
