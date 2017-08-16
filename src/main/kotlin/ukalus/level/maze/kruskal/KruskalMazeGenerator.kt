package ukalus.level.maze.kruskal

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.level.IntLevel
import ukalus.level.Region
import ukalus.level.RegionFactory
import ukalus.level.maze.MazeRegion
import ukalus.level.maze.path
import ukalus.level.maze.wall
import ukalus.math.Vector2D
import ukalus.util.DisjointSet
import ukalus.util.shuffle
import java.util.*

class KruskalMazeGenerator(private val random: Random, height: Int, width: Int) : RegionFactory<Int> {
    private val height: Int = (height - 1 and Integer.MAX_VALUE - 1) + 1
    private val width: Int = (width - 1 and Integer.MAX_VALUE - 1) + 1

    override fun create(): Region<Int> {
        val nodes = Array(height) { x -> IntArray(width) { y -> if (x % 2 == 1 && y % 2 == 1) 1 else 0 } }
        val edges = mutableListOf<EdgeCell>()

        // add walls / open cells
        for (x in 1 until nodes.size - 1) {
            for (y in 1 until nodes[x].size - 1) {
                if (x % 2 == 0 && y % 2 == 1) {
                    // horizontal wall
                    edges.add(EdgeCell(x, y, (x - 1) * width + y, (x + 1) * width + y))
                } else if (x % 2 == 1 && y % 2 == 0) {
                    // vertical wall
                    edges.add(EdgeCell(x, y, x * width + y - 1, x * width + y + 1))
                }
            }
        }

        val sets = DisjointSet(height * width)
        edges
                .shuffle(random)
                .forEach { (x, y, left, right) ->
                    if (sets.find(left) != sets.find(right)) {
                        sets.union(left, right)
                        nodes[x][y] = path
                    }
                }

        return MazeRegion(nodes)
    }
}

fun main(vararg args: String) {
    val generator = KruskalMazeGenerator(RandomAdaptor(MersenneTwister()), 19, 79)

    val level = IntLevel(Array(19) { Array(79, { wall }) })
    val region = generator.create()
    region.place(Vector2D(0, 0), level)

    println(level)
}
