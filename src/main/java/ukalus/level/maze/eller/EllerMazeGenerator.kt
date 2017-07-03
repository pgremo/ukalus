package ukalus.level.maze.eller

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.level.Level
import ukalus.level.Region
import ukalus.level.RegionFactory
import ukalus.level.maze.MazeRegion
import ukalus.math.Vector2D
import ukalus.util.DisjointSet
import java.util.*

class EllerMazeGenerator(private val random: Random, height: Int, width: Int) : RegionFactory<Int> {
    private val height: Int = (height - 1 and Integer.MAX_VALUE - 1) + 1
    private val width: Int = (width - 1 and Integer.MAX_VALUE - 1) + 1

    override fun create(): Region<Int> {
        val result = Array(height) { IntArray(width) }

        val sets = DisjointSet(width / 2)

        var x = 1
        while (x < height) {
            // horizontal passages
            val current = result[x]
            current[1] = 1
            for (right in 1..width / 2 - 1) {
                current[right * 2] = 1
                val left = right - 1
                if (sets.find(left) != sets.find(right) && random.nextBoolean()) {
                    sets.union(left, right)
                    current[right * 2 - 1] = 1
                }
            }
            // vertical passages
            val next = result[x + 1]
            for (y in 1..width / 2 - 1) {
                if (sets.size(y) == 1) {
                    next[y * 2] = 1
                }
            }
            x += 2
        }

        return MazeRegion(result)
    }

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            val generator = EllerMazeGenerator(RandomAdaptor(MersenneTwister()), 19, 79)

            val level = Level(Array(19) { Array(79, { 0 }) })
            val region = generator.create()
            region.place(Vector2D(0, 0), level)

            println(level)
        }
    }

}
