package ukalus.level.maze.depthfirst

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.graph.Node
import ukalus.graph.NodeStack
import ukalus.graph.NodeTraversal
import ukalus.level.IntLevel
import ukalus.level.Region
import ukalus.level.RegionFactory
import ukalus.level.maze.*
import ukalus.math.Vector2D
import java.util.*


class DepthFirstMazeGenerator(private val random: Random, height: Int, width: Int) : RegionFactory<Int> {
    private val height: Int = (height - 1 and Integer.MAX_VALUE - 1) + 1
    private val width: Int = (width - 1 and Integer.MAX_VALUE - 1) + 1

    override fun create(): Region<Int> {
        val nodes = HashMap<Vector2D, MazeNode>()
        val edges = HashMap<Vector2D, MazeEdge>()

        val cells = Array(height) { IntArray(width) }

        // create nodes / edges
        for (x in 1 until cells.size - 1) {
            for (y in 1 until cells[x].size - 1) {
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
                    cells[x][y] = path
                }
            }
        }

        val route = HashSet<MazeEdge>()

        val start = ArrayList<Node>(nodes.values)[random.nextInt(nodes.size)]

        val traversal = NodeTraversal(MazeTraversalDelegate(start, route, random), NodeStack())
        traversal.traverse(start)

        for (edge in route) {
            cells[edge.location.x][edge.location.y] = path
        }

        return MazeRegion(cells)
    }

}

fun main(vararg args: String) {
    val generator = DepthFirstMazeGenerator(RandomAdaptor(MersenneTwister()), 19, 79)

    val level = IntLevel(Array(19) { Array(79) { wall } })

    generator.create().place(Vector2D(0, 0), level)

    println(level)
}
