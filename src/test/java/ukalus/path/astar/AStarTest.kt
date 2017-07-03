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
        val map = Level(arrayOf(arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE)))
        val finder = AStar()
        val cost = FixedCost(1)
        val start = Node2D(map, Vector2D(0, 0), null)
        val stop = Node2D(map, Vector2D(2, 2), null)
        val heuristic = ManhattanHeuristic(Vector2D(0, 0), Vector2D(2, 2))
        val path = finder.solve(heuristic, cost, start, stop)
        TestCase.assertTrue(path.hasNext())

        TestCase.assertEquals(Vector2D(0, 0), (path.next() as Node2D).location)
        TestCase.assertEquals(Vector2D(1, 0), (path.next() as Node2D).location)
        TestCase.assertEquals(Vector2D(1, 1), (path.next() as Node2D).location)
        TestCase.assertEquals(Vector2D(1, 2), (path.next() as Node2D).location)
        TestCase.assertEquals(Vector2D(2, 2), (path.next() as Node2D).location)

    }

    fun testFindPathChoke() {
        val finder = AStar()
        val cost = FixedCost(1)
        val map = Level(arrayOf(arrayOf(TRUE, null, TRUE), arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, null, TRUE)))
        val start = Node2D(map, Vector2D(0, 0), null)
        val stop = Node2D(map, Vector2D(2, 2), null)
        val heuristic = ManhattanHeuristic(Vector2D(0, 0), Vector2D(2, 2))
        val path = finder.solve(heuristic, cost, start, stop)
        TestCase.assertTrue(path.hasNext())

        TestCase.assertEquals(Vector2D(0, 0), (path.next() as Node2D).location)
        TestCase.assertEquals(Vector2D(1, 0), (path.next() as Node2D).location)
        TestCase.assertEquals(Vector2D(1, 1), (path.next() as Node2D).location)
        TestCase.assertEquals(Vector2D(1, 2), (path.next() as Node2D).location)
        TestCase.assertEquals(Vector2D(2, 2), (path.next() as Node2D).location)
    }
}