package ukalus.graph

import java.util.Arrays.asList

open class Edge(private val from: Node, private val to: Node) {

    val nodes: List<Node>
        get() = asList(from, to)

    fun getNode(node: Node) = when (node) {
        from -> to
        to -> from
        else -> null
    }

}