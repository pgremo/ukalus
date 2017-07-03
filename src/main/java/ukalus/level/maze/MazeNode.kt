package ukalus.level.maze

import ukalus.graph.Edge
import ukalus.graph.Node
import ukalus.math.Vector2D

class MazeNode(val location: Vector2D) : Node {
    override val edges = mutableListOf<Edge>()

    fun addEdge(edge: Edge) {
        edges.add(edge)
    }

    override fun toString() = location.toString()
}
