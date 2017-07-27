package ukalus.level.maze

import ukalus.graph.Edge
import ukalus.graph.GraphTraversalDelegate
import ukalus.graph.Node
import ukalus.util.random
import java.util.*

class MazeTraversalDelegate(start: Node, private val path: MutableSet<MazeEdge>, private val random: Random) : GraphTraversalDelegate {
    private val visited = mutableSetOf<Node?>(start)

    override fun getEdge(node: Node) = node.edges.filter { !visited.contains(it.getNode(node)) }.random(random)

    override fun traverse(node: Node, edge: Edge) {
        path.add(edge as MazeEdge)
        visited.add(edge.getNode(node))
    }
}
