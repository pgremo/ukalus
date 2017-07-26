/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar

/**
 * @author gremopm
 */
fun fixedCost(cost: Int): (Node, Node) -> Double {
    return { _: Node, _: Node -> cost.toDouble() }
}