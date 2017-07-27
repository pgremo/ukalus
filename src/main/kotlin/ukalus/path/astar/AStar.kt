package ukalus.path.astar

import java.util.*
import java.util.Comparator.comparingDouble

/**
 * @author gremopm
 */
class AStar {
    fun solve(heuristic: (Node) -> Double, cost: (Node, Node) -> Double, start: Node, stop: Node): List<Node> {
        val open = PriorityQueue<Node>(1, comparingDouble(Node::cost)).apply { add(start) }
        val close = mutableListOf<Node>()
        var current = start
        while (!open.isEmpty() && current != stop) {
            current = open.poll()!!
            for (successor in current.successors) {
                val newCost = cost(current, successor)
                if (successor !in open && successor !in close || successor.cost > newCost) {
                    successor.cost = newCost
                    successor.estimate = heuristic(successor)
                    close.remove(successor)
                    open.add(successor)
                }
            }
            close.add(current)
        }
        return if (current == stop)
            generateSequence(current) { it.parent }.fold(mutableListOf()) { s, x -> s.apply { add(0, x) } }
        else
            emptyList()
    }
}