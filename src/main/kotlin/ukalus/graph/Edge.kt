package ukalus.graph

open class Edge(private val from: Node, private val to: Node) {

    val nodes: List<Node>
        get() = listOf(from, to)

    fun getNode(node: Node) = when (node) {
        from -> to
        to -> from
        else -> null
    }

}