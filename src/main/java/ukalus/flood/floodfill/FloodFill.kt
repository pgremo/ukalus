package ukalus.flood.floodfill

import java.util.*

class FloodFill {

    fun getTemplate(distance: Int, start: Node): Set<Node> {
        val result = HashSet<Node>()
        val open = LinkedList<Node>()
        open.add(start)
        while (!open.isEmpty()) {
            val current = open.remove()
            current.children
                    .filter { it.distance <= distance }
                    .filter { it !in open }
                    .forEach { open.add(it) }
            result.add(current)
        }
        return result
    }
}
