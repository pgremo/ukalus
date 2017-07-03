/*
 * Created on Feb 11, 2005
 *
 */
package ukalus.path.astar

import ukalus.level.Level
import ukalus.math.Vector2D

import junit.framework.TestCase

import java.lang.Boolean.*

/**
 * @author gremopm
 */
class Node2DTest : TestCase() {

    fun testGetSuccessorsFromMiddle() {
        val map = Level(arrayOf(arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE)))
        val parent = Node2D(map, Vector2D(1, 0), null)
        val current = Node2D(map, Vector2D(1, 1), parent)
        val successors = current.successors
        TestCase.assertEquals(3, successors.size)
    }

    fun testGetSuccessorsFromCorner() {
        val map = Level(arrayOf(arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE)))
        val parent = Node2D(map, Vector2D(1, 0), null)
        val current = Node2D(map, Vector2D(0, 0), parent)
        val successors = current.successors
        TestCase.assertEquals(1, successors.size)
    }

    fun testGetSuccessorsFromEdge() {
        val map = Level(arrayOf(arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE)))
        val parent = Node2D(map, Vector2D(0, 0), null)
        val current = Node2D(map, Vector2D(0, 1), parent)
        val successors = current.successors
        TestCase.assertEquals(2, successors.size)
    }

    fun testGetSuccessorsOriginInMiddle() {
        val map = Level(arrayOf(arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE), arrayOf(TRUE, TRUE, TRUE)))
        val current = Node2D(map, Vector2D(1, 1), null)
        val successors = current.successors
        TestCase.assertEquals(4, successors.size)
    }
}