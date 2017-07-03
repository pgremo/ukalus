package ukalus.level.maze.kruskal

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.level.Level
import ukalus.level.Region
import ukalus.level.RegionFactory
import ukalus.level.maze.MazeRegion
import ukalus.util.shuffle
import ukalus.math.Vector2D
import ukalus.util.DisjointSet
import java.util.*

class KruskalMazeGenerator(private val random: Random, height: Int, width: Int) : RegionFactory<Int> {
    private val height: Int = (height - 1 and Integer.MAX_VALUE - 1) + 1
    private val width: Int = (width - 1 and Integer.MAX_VALUE - 1) + 1

    override fun create(): Region<Int> {
        val nodes = Array(height) { IntArray(width) }
        val edges = ArrayList<EdgeCell>(height * width)

        // add walls / open cells
        for (x in 1..nodes.size - 1 - 1) {
            for (y in 1..nodes[x].size - 1 - 1) {
                if (x % 2 == 0 && y % 2 == 1) {
                    // horizontal wall
                    edges.add(EdgeCell(x, y, (x - 1) * width + y, (x + 1) * width + y))
                } else if (x % 2 == 1 && y % 2 == 0) {
                    // vertical wall
                    edges.add(EdgeCell(x, y, x * width + y - 1, x * width + y + 1))
                } else if (x % 2 == 1 && y % 2 == 1) {
                    // open cell
                    nodes[x][y] = 1
                }
            }
        }

        val sets = DisjointSet(height * width)
        edges
                .shuffle(random)
                .forEach { wall ->
                    if (sets.find(wall.left) != sets.find(wall.right)) {
                        sets.union(wall.left, wall.right)
                        nodes[wall.x][wall.y] = 1
                    }
                }

        return MazeRegion(nodes)
    }

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            val generator = KruskalMazeGenerator(RandomAdaptor(MersenneTwister()), 19, 79)

            val level = Level(Array(19) { Array(79, { 0 }) })
            val region = generator.create()
            region.place(Vector2D(0, 0), level)

            println(level)
        }
    }

}
