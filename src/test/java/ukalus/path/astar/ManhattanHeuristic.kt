/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar

import ukalus.math.Vector2D

/**
 * @author gremopm
 */
class ManhattanHeuristic(start: Vector2D, private val stop: Vector2D) : Heuristic {
    private val v2: Vector2D = start - stop

    override fun estimate(current: Node): Double {
        val (x, y) = (current as Node2D).location - stop
        val heuristic = (Math.abs(x) + Math.abs(y)).toDouble()
        val cross = Math.abs(x * v2.y - v2.x * y).toDouble()
        return heuristic + cross * 0.001
    }
}