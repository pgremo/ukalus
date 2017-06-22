/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar

/**
 * @author gremopm
 */
interface Cost {

    fun calculate(previous: Node, current: Node): Double

}