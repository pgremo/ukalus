/*
 * Created on Feb 15, 2005
 *
 */
package ukalus.path.astar

import ukalus.math.Vector2D
import junit.framework.TestCase
import ukalus.level.Level

/**
 * @author gremopm
 */
class ManhattanHeuristicTest : TestCase() {

    fun testDistance() {
        val heuristic = ManhattanHeuristic(Vector2D(0, 0), Vector2D(2, 2))
        val closer = Node2D(Level(arrayOf(emptyArray<Int>())), Vector2D(1, 1), null)
        val further = Node2D(Level(arrayOf(emptyArray<Int>())), Vector2D(0, 2), null)
        TestCase.assertEquals(2.0, heuristic.estimate(closer), 0.0)
        TestCase.assertEquals(2.004, heuristic.estimate(further), 0.0)
    }

}