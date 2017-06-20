package ukalus.graph

interface GraphTraversalDelegate {

    fun getNode(node: Node): Edge?

    fun traverse(node: Node, edge: Edge)

}