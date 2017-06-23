package ukalus.level.maze

import ukalus.graph.Edge
import ukalus.graph.Node
import ukalus.math.Vector2D

class MazeEdge(val location: Vector2D, head: Node, tail: Node) : Edge(head, tail) {
    override fun toString() = location.toString()
}
