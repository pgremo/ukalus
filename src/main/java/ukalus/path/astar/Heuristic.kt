/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar

/**
 * @author gremopm
 */
interface Heuristic {

    fun estimate(current: Node): Double

}