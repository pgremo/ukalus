package ukalus.blast

import org.junit.Test
import ukalus.level.Level
import ukalus.math.Vector2D

import java.util.HashSet

import org.junit.Assert.*

abstract class BlastTestCase {

    lateinit var blast: Blast

    @Test
    fun testSeeAll() {
        val area = arrayOf(arrayOf(FLOOR, FLOOR, FLOOR, FLOOR, FLOOR), arrayOf(FLOOR, FLOOR, FLOOR, FLOOR, FLOOR), arrayOf(FLOOR, FLOOR, FLOOR, FLOOR, FLOOR), arrayOf(FLOOR, FLOOR, FLOOR, FLOOR, FLOOR), arrayOf(FLOOR, FLOOR, FLOOR, FLOOR, FLOOR))
        val expected = HashSet<Vector2D>()
        for (i in area.indices) {
            (0..area[i].size - 1).mapTo(expected) { Vector2D(i, it) }
        }
        expected.remove(Vector2D(0, 0))
        expected.remove(Vector2D(4, 0))
        expected.remove(Vector2D(0, 4))
        expected.remove(Vector2D(4, 4))

        val level = Level(area)
        val actual = blast.getTemplate(Vector2D(2, 2), LevelScanner(level, WALL), 2)
        printSight(actual, level)
        assertNotNull(actual)
        assertTrue(actual.containsAll(expected))
        assertEquals(actual.size.toLong(), expected.size.toLong())
    }

    @Test
    fun testSeeAllWalls() {
        val area = arrayOf(arrayOf(WALL, WALL, WALL, WALL, WALL), arrayOf(WALL, FLOOR, FLOOR, FLOOR, WALL), arrayOf(WALL, FLOOR, FLOOR, FLOOR, WALL), arrayOf(WALL, FLOOR, FLOOR, FLOOR, WALL), arrayOf(WALL, WALL, WALL, WALL, WALL))
        val expected = HashSet<Vector2D>()
        for (i in area.indices) {
            (0..area[i].size - 1).mapTo(expected) { Vector2D(i, it) }
        }
        expected.remove(Vector2D(0, 0))
        expected.remove(Vector2D(4, 0))
        expected.remove(Vector2D(0, 4))
        expected.remove(Vector2D(4, 4))

        val level = Level(area)
        val actual = blast.getTemplate(Vector2D(2, 2), LevelScanner(level, WALL), 2)
        printSight(actual, level)
        assertNotNull(actual)
        assertTrue(actual.containsAll(expected))
        assertEquals(actual.size.toLong(), expected.size.toLong())
    }

    @Test
    fun testSeeAllCorners() {
        val area = arrayOf(arrayOf(WALL, WALL, WALL, WALL, WALL), arrayOf(WALL, FLOOR, FLOOR, FLOOR, WALL), arrayOf(WALL, FLOOR, FLOOR, FLOOR, WALL), arrayOf(WALL, FLOOR, FLOOR, FLOOR, WALL), arrayOf(WALL, WALL, WALL, WALL, WALL))
        val expected = HashSet<Vector2D>()
        for (i in area.indices) {
            (0..area[i].size - 1).mapTo(expected) { Vector2D(i, it) }
        }

        val level = Level(area)
        val actual = blast.getTemplate(Vector2D(2, 2), LevelScanner(level, WALL), 3)
        printSight(actual, level)
        assertNotNull(actual)
        assertTrue(actual.containsAll(expected))
        assertEquals(expected.size.toLong(), actual.size.toLong())
    }

    @Test
    fun testSeeAllCornersSmall() {
        val area = arrayOf(arrayOf(WALL, WALL, WALL), arrayOf(WALL, FLOOR, WALL), arrayOf(WALL, WALL, WALL))
        val expected = HashSet<Vector2D>()
        for (i in area.indices) {
            (0..area[i].size - 1).mapTo(expected) { Vector2D(i, it) }
        }

        val level = Level(area)
        val actual = blast.getTemplate(Vector2D(1, 1), LevelScanner(level, WALL), 3)
        printSight(actual, level)
        assertNotNull(actual)
        assertTrue(actual.containsAll(expected))
        assertEquals(actual.size.toLong(), expected.size.toLong())
    }

    @Test
    fun testSeeAllWallsSmallRoom() {
        val area = arrayOf(arrayOf(WALL, WALL, WALL, WALL, WALL, WALL, WALL), arrayOf(WALL, WALL, WALL, WALL, WALL, WALL, WALL), arrayOf(WALL, WALL, FLOOR, FLOOR, FLOOR, WALL, WALL), arrayOf(WALL, WALL, FLOOR, FLOOR, FLOOR, WALL, WALL), arrayOf(WALL, WALL, FLOOR, FLOOR, FLOOR, WALL, WALL), arrayOf(WALL, WALL, WALL, WALL, WALL, WALL, WALL), arrayOf(WALL, WALL, WALL, WALL, WALL, WALL, WALL))
        val expected = HashSet<Vector2D>()
        for (i in 1..area.size - 1 - 1) {
            (1..area[i].size - 1 - 1).mapTo(expected) { Vector2D(i, it) }
        }
        expected.remove(Vector2D(1, 1))
        expected.remove(Vector2D(5, 1))
        expected.remove(Vector2D(1, 5))
        expected.remove(Vector2D(5, 5))

        val level = Level(area)
        val actual = blast.getTemplate(Vector2D(3, 3), LevelScanner(level, WALL), 2)
        printSight(actual, level)
        assertNotNull(actual)
        assertTrue(actual.containsAll(expected))
        assertEquals(actual.size.toLong(), expected.size.toLong())
    }

    @Test
    fun testSeePartialRoom() {
        val area = arrayOf(arrayOf(WALL, WALL, WALL, WALL, WALL, WALL), arrayOf(WALL, WALL, WALL, FLOOR, FLOOR, WALL), arrayOf(WALL, WALL, WALL, FLOOR, FLOOR, WALL), arrayOf(WALL, FLOOR, FLOOR, FLOOR, FLOOR, WALL), arrayOf(WALL, WALL, WALL, FLOOR, FLOOR, WALL), arrayOf(WALL, WALL, WALL, FLOOR, FLOOR, WALL), arrayOf(WALL, WALL, WALL, WALL, WALL, WALL))
        val expected = HashSet<Vector2D>()
        expected.add(Vector2D(1, 5))
        expected.add(Vector2D(2, 0))
        expected.add(Vector2D(2, 1))
        expected.add(Vector2D(2, 2))
        expected.add(Vector2D(2, 3))
        expected.add(Vector2D(2, 4))
        expected.add(Vector2D(2, 5))
        expected.add(Vector2D(3, 0))
        expected.add(Vector2D(3, 1))
        expected.add(Vector2D(3, 2))
        expected.add(Vector2D(3, 3))
        expected.add(Vector2D(3, 4))
        expected.add(Vector2D(3, 5))
        expected.add(Vector2D(4, 0))
        expected.add(Vector2D(4, 1))
        expected.add(Vector2D(4, 2))
        expected.add(Vector2D(4, 3))
        expected.add(Vector2D(4, 4))
        expected.add(Vector2D(4, 5))
        expected.add(Vector2D(5, 5))
        val level = Level(area)
        val actual = blast.getTemplate(Vector2D(3, 1), LevelScanner(level, WALL), 5)
        printSight(actual, level)
        assertNotNull(actual)
        assertTrue(actual.containsAll(expected))
        assertEquals(actual.size.toLong(), expected.size.toLong())
    }

    private fun printSight(list: Set<Vector2D>, level: Level<*>) {
        for (i in 0..level.length - 1) {
            (0..level.width - 1)
                    .map { Vector2D(i, it) }
                    .forEach {
                        if (list.contains(it)) {
                            print(if (level[it] == WALL) "#" else ".")
                        } else {
                            print(" ")
                        }
                    }
            println()
        }
    }

    companion object {
        private val WALL = Any()
        private val FLOOR = Any()
    }

}
