package ukalus.path.astar

import java.util.*
import java.util.Comparator.comparingDouble

/**
 * @author gremopm
 */
class AStar {

    fun solve(heuristic: Heuristic, cost: Cost, start: Node?, stop: Node?): Iterator<Node> {
        if (start == null || stop == null) {
            throw IllegalArgumentException()
        }

        val open = PriorityQueue<Node>(1, comparingDouble(Node::cost))
        val close = LinkedList<Node>()
        var current = start
        open.add(start)
        while (!open.isEmpty() && current != stop) {
            current = open.poll()
            for (successor in current!!.successors) {
                val newCost = cost.calculate(current, successor)
                val openNode = open.find { successor == it }
                if (openNode == null || openNode.cost > newCost) {
                    val closeNode = close.find { successor == it }
                    if (closeNode == null || closeNode.cost > newCost) {
                        successor.cost = newCost
                        successor.estimate = heuristic.estimate(successor)
                        open.remove(openNode)
                        close.remove(closeNode)
                        open.add(successor)
                    }
                }
            }
            close.add(current)
        }

        val result = LinkedList<Node>()
        while (current != null) {
            result.addFirst(current)
            current = current.parent
        }

        return result.iterator()
    }
}