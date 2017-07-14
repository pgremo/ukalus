package ukalus.level.maze.eller

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.level.IntLevel
import ukalus.level.RegionFactory
import ukalus.level.maze.MazeRegion
import ukalus.math.Vector2D
import ukalus.util.compareToAndSwap
import java.util.*

class EllerMazeGenerator(private val random: Random,
                         private val height: Int,
                         private val width: Int
) : RegionFactory<Int> {

    override fun create(): MazeRegion {
        var nextSet = 0
        var current = IntArray(width - 2) { if (it % 2 == 1) wall else --nextSet }
        var next = current.copyOf()

        val maze: Array<IntArray> = Array(height) { IntArray(width) { wall } }

        for (q in 0 until (height - 1) / 2 - 1) {

            join(current)
            cut(current, next)

            (next.indices step 2)
                    .filter { next[it] == unknown }
                    .forEach { next[it] = --nextSet }

            (1 until next.size step 2)
                    .forEach { next[it] = wall }


            for (k in current.indices) {
                if (current[k] == wall) {
                    maze[2 * q + 1][k + 1] = wall
                    maze[2 * q + 2][k + 1] = wall
                } else {
                    maze[2 * q + 1][k + 1] = path
                    if (current[k] == next[k]) maze[2 * q + 2][k + 1] = path
                }
            }

            current = next.copyOf()
            next = IntArray(width - 2) { unknown }
        }

        join(current) { true }

        for (k in current.indices) {
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
        var beginning: Int = 0
        var end: Int
        do {
            var i = beginning
            while (i < current.size - 1 && current[i] == current[i + 2]) {
                i += 2
            }
            end = i

            var madeVertical = false
            do {
                for (j in beginning..end step 2) {
                    if (random.nextBoolean()) {
                        next[j] = current[j]
                        madeVertical = true
                    }
                }
            } while (!madeVertical)

            beginning = end + 2

        } while (end != current.size - 1)
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
