/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar

/**
 * @author gremopm
 */
class FixedCost(private val cost: Int) : Cost {

    override fun calculate(previous: Node, current: Node) = cost.toDouble()

}