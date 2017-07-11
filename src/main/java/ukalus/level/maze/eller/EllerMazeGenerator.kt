package ukalus.level.maze.eller

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.level.Level
import ukalus.level.RegionFactory
import ukalus.level.maze.MazeRegion
import ukalus.math.Vector2D
import java.lang.System.arraycopy
import java.util.*
import java.util.Arrays.fill

class EllerMazeGenerator(private val random: Random,
                         private val height: Int,
                         private val width: Int
) : RegionFactory<Int> {

    override fun create(): MazeRegion {
        val maze: Array<IntArray> = Array(height) { IntArray(width) { wall } }

        val current = IntArray((width - 1) / 2 * 2 - 1)
        for (i in current.indices step 2) {
            current[i] = i / 2 + 1
            if (i != current.size - 1)
                current[i + 1] = setWall
        }

        val next = IntArray(current.size) { current[it] }

        var nextSet = current[current.size - 1]



        for (q in 0 until (height - 1) / 2 - 1) {

            join(current)
            cut(current, next)

            for (j in current.indices step 2) {

                if (next[j] == unknown)
                    next[j] = ++nextSet

                if (j != current.size - 1)
                    next[j + 1] = setWall
            }


            for (k in current.indices) {

                if (current[k] == setWall) {
                    maze[2 * q + 1][k + 1] = wall
                    maze[2 * q + 2][k + 1] = wall
                } else {
                    maze[2 * q + 1][k + 1] = path

                    if (current[k] == next[k]) {
                        maze[2 * q + 2][k + 1] = path
                    }
                }

            }

            arraycopy(next, 0, current, 0, current.size)
            fill(next, unknown)

        }

        join(current) { true }

        for (k in current.indices) {
            maze[maze.size - 2][k + 1] = if (current[k] == setWall) wall else path
        }

        return MazeRegion(maze)
    }

    private fun join(items: IntArray, combine: () -> Boolean = random::nextBoolean) {
        for (i in 1 until items.size step 2) {
            if (items[i] == setWall && items[i - 1] != items[i + 1] && combine()) {

                items[i] = 0

                val (previous, next) = items[i - 1].compareAndSwap(items[i + 1])

                items.indices
                        .filter { items[it] == previous }
                        .forEach { items[it] = next }
            }
        }
    }

    private fun cut(current: IntArray, next: IntArray) {
        var begining: Int = 0
        var end: Int
        do {
            var i = begining
            while (i < current.size - 1 && current[i] == current[i + 2]) {
                i += 2
            }
            end = i
            var madeVertical = false
            do {
                for (j in begining..end step 2) {
                    if (random.nextBoolean()) {
                        next[j] = current[j]
                        madeVertical = true
                    }
                }
            } while (!madeVertical)

            begining = end + 2

        } while (end != current.size - 1)
    }

    companion object {
        private val wall = 0
        private val path = 1
        private val unknown = -2
        private val setWall = -1

        @JvmStatic fun main(args: Array<String>) {
            val generator = EllerMazeGenerator(RandomAdaptor(MersenneTwister()), 19, 79)

            val level = Level(Array(19) { Array(79, { 0 }) })
            val region = generator.create()
            region.place(Vector2D(0, 0), level)

            println(level)
        }
    }

}

fun Int.compareAndSwap(other: Int): Pair<Int, Int> = if (this < other) Pair(this, other) else Pair(other, this)
