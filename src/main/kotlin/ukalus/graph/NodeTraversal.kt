package ukalus.graph

class NodeTraversal(private val delegate: GraphTraversalDelegate, private val visited: NodeCollection) {

    fun traverse(root: Node) {
        visited.add(root)
        while (!visited.isEmpty) {
            val node = visited.get()!!
            val edge = delegate.getEdge(node)
            if (edge != null) {
                delegate.traverse(node, edge)
                visited.add(edge.getNode(node)!!)
            } else {
                visited.remove()
            }
        }
    }
}