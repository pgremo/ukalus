/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar

import ukalus.math.Vector2D

/**
 * @author gremopm
 */
fun manhattanHeuristic(start: Vector2D, stop: Vector2D): (Node) -> Double {
    val v2: Vector2D = start - stop

    return { current: Node ->
        val (x, y) = (current as Node2D).location - stop
        val heuristic = (Math.abs(x) + Math.abs(y)).toDouble()
        val cross = Math.abs(x * v2.y - v2.x * y).toDouble()
        heuristic + cross * 0.001
    }
}