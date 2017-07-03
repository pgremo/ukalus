package ukalus.level.maze.breadthfirst

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.graph.NodeQueue
import ukalus.graph.NodeTraversal
import ukalus.level.Level
import ukalus.level.Region
import ukalus.level.RegionFactory
import ukalus.level.maze.MazeEdge
import ukalus.level.maze.MazeNode
import ukalus.level.maze.MazeRegion
import ukalus.level.maze.MazeTraversalDelegate
import ukalus.math.Vector2D
import java.util.*


class BreadthFirstMazeGenerator(private val random: Random, height: Int, width: Int) : RegionFactory<Int> {
    private val height: Int = (height - 1 and Integer.MAX_VALUE - 1) + 1
    private val width: Int = (width - 1 and Integer.MAX_VALUE - 1) + 1

    override fun create(): Region<Int> {
        val nodes = HashMap<Vector2D, MazeNode>()
        val edges = HashMap<Vector2D, MazeEdge>()

        val cells = Array(height) { IntArray(width) }

        // create nodes / edges
        for (x in 1..cells.size - 1 - 1) {
            for (y in 1..cells[x].size - 1 - 1) {
                if (x % 2 == 0 && y % 2 == 1) {
                    // vertical edge
                    val head = nodes.computeIfAbsent(Vector2D(x - 1, y), { MazeNode(it) })
                    val tail = nodes.computeIfAbsent(Vector2D(x + 1, y), { MazeNode(it) })
                    val edge = edges.computeIfAbsent(Vector2D(x, y)) { MazeEdge(it, head, tail) }
                    head.addEdge(edge)
                    tail.addEdge(edge)
                } else if (x % 2 == 1 && y % 2 == 0) {
                    // horizontal edge
                    val head = nodes.computeIfAbsent(Vector2D(x, y - 1), { MazeNode(it) })
                    val tail = nodes.computeIfAbsent(Vector2D(x, y + 1), { MazeNode(it) })
                    val edge = edges.computeIfAbsent(Vector2D(x, y)) { MazeEdge(it, head, tail) }
                    head.addEdge(edge)
                    tail.addEdge(edge)
                } else if (x % 2 == 1 && y % 2 == 1) {
                    // node
                    nodes.computeIfAbsent(Vector2D(x, y), { MazeNode(it) })
                    cells[x][y] = 1
                }
            }
        }

        val path = HashSet<MazeEdge>()

        val start = ArrayList<MazeNode>(nodes.values)[random.nextInt(nodes.size)]

        val traversal = NodeTraversal(MazeTraversalDelegate(start, path, random), NodeQueue())
        traversal.traverse(start)

        for (edge in path) {
            cells[edge.location.x][edge.location.y] = 1
        }

        return MazeRegion(cells)
    }

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            val generator = BreadthFirstMazeGenerator(RandomAdaptor(MersenneTwister()), 19, 79)

            val level = Level(Array(19) { Array(79) { 0 } })
            generator.create().place(Vector2D(0, 0), level)

            println(level)
        }
    }

}
