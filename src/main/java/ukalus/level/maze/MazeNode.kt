package ukalus.level.maze

import ukalus.graph.Edge
import ukalus.graph.Node
import ukalus.math.Vector2D
import java.util.*

class MazeNode(val location: Vector2D) : Node {
    override val edges = LinkedList<Edge>()

    fun addEdge(edge: Edge) {
        edges.add(edge)
    }

    override fun toString(): String = location.toString()
}
