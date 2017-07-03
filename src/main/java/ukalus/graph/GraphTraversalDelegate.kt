package ukalus.graph

interface GraphTraversalDelegate {

    fun getEdge(node: Node): Edge?

    fun traverse(node: Node, edge: Edge)

}