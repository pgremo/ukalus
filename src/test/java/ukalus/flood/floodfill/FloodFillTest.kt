package ukalus.flood.floodfill

import junit.framework.TestCase
import ukalus.level.Level
import ukalus.math.Vector2D

class FloodFillTest : TestCase() {

    fun testFill1() {
        val data = arrayOf(arrayOf(Node.FLOOR, Node.FLOOR, Node.FLOOR), arrayOf(Node.FLOOR, Node.FLOOR, Node.FLOOR), arrayOf(Node.FLOOR, Node.FLOOR, Node.FLOOR))
        val actual = FloodFill().getTemplate(1, Node(Level(data), Vector2D(1, 1), 0))
        assertEquals(9, actual.size)
    }

    fun testFill1WithObstacles() {
        val data = arrayOf(arrayOf(Node.FLOOR, Node.WALL, Node.FLOOR), arrayOf(Node.FLOOR, Node.FLOOR, Node.FLOOR), arrayOf(Node.FLOOR, Node.WALL, Node.FLOOR))
        val actual = FloodFill().getTemplate(1, Node(Level(data), Vector2D(1, 1), 0))
        assertEquals(7, actual.size)
    }

    fun testFill1WithBlock() {
        val data = arrayOf(arrayOf(Node.FLOOR, Node.WALL, Node.FLOOR, Node.FLOOR), arrayOf(Node.FLOOR, Node.FLOOR, Node.FLOOR, Node.FLOOR), arrayOf(Node.FLOOR, Node.FLOOR, Node.WALL, Node.FLOOR), arrayOf(Node.FLOOR, Node.FLOOR, Node.FLOOR, Node.FLOOR), arrayOf(Node.FLOOR, Node.WALL, Node.FLOOR, Node.FLOOR))
        val actual = FloodFill().getTemplate(10, Node(Level(data), Vector2D(2, 0), 0))
        assertEquals(17, actual.size)

        val target = actual.first { it.location == Vector2D(2, 3) }

        assertNotNull(target)
        assertEquals(3, target.distance)
    }


}
