/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar

/**
 * @author gremopm
 */
interface Node {

    val parent: Node

    val successors: Collection<Node>

    var cost: Double

    var estimate: Double

    val total: Double
}