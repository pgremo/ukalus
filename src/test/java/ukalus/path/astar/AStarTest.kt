/*
 * Created on Feb 15, 2005
 *
 */
package ukalus.path.astar

import junit.framework.TestCase
import ukalus.level.Level
import ukalus.math.Vector2D
import java.lang.Boolean.TRUE

/**
 * @author gremopm
 */
class AStarTest : TestCase() {

    fun testFindPathNoObstacles() {
        val heuristic = manhattanHeuristic(Vector2D(0, 0), Vector2D(2, 2))
        val cost = fixedCost(1)
        val map = Level(arrayOf(arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE)))
        val start = Node2D(map, Vector2D(0, 0), null)
        val stop = Node2D(map, Vector2D(2, 2), null)
        val path = AStar().solve(heuristic, cost, start, stop).map { (it as Node2D).location }

        assertEquals(listOf(Vector2D(0, 0), Vector2D(1, 0), Vector2D(1, 1), Vector2D(1, 2), Vector2D(2, 2)), path)
    }

    fun testFindPathChoke() {
        val heuristic = manhattanHeuristic(Vector2D(0, 0), Vector2D(2, 2))
        val cost = fixedCost(1)
        val map = Level(arrayOf(arrayOf(TRUE, null, TRUE), arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, null, TRUE)))
        val start = Node2D(map, Vector2D(0, 0), null)
        val stop = Node2D(map, Vector2D(2, 2), null)
        val path = AStar().solve(heuristic, cost, start, stop).map { (it as Node2D).location }

        assertEquals(listOf(Vector2D(0, 0), Vector2D(1, 0), Vector2D(1, 1), Vector2D(1, 2), Vector2D(2, 2)), path)
    }
}