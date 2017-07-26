package ukalus.math

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.HashMap

import junit.framework.TestCase

class Vector2DTest : TestCase() {

    fun testRotate() {
        val direction = Vector2D(0, -1)
        TestCase.assertEquals(Vector2D(0, 0), Vector2D(0, 0)
                .rotate(direction))
        TestCase.assertEquals(Vector2D(-1, 0), Vector2D(0, -1)
                .rotate(direction))
        TestCase.assertEquals(Vector2D(-2, 0), Vector2D(0, -2)
                .rotate(direction))
        TestCase.assertEquals(Vector2D(-3, 0), Vector2D(0, -3)
                .rotate(direction))
    }

    fun testFindInHashMap() {
        val map = HashMap<Vector2D, Any?>()
        map.put(Vector2D(1, 0), null)
        TestCase.assertTrue(map.containsKey(Vector2D(1, 0)))
    }

    fun testAdd() {
        var result = Vector2D(1, 0)
        val angle = Vector2D(1, 1)
        result = result.plus(angle)
        TestCase.assertEquals(Vector2D(2, 1), result)
    }

    fun testSubtract() {
        var result = Vector2D(1, 0)
        val angle = Vector2D(1, 1)
        result = result.minus(angle)
        TestCase.assertEquals(Vector2D(0, -1), result)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    fun testSerialization() {
        val expected = Vector2D(1, 1)
        val out = ByteArrayOutputStream()
        val oout = ObjectOutputStream(out)
        oout.writeObject(expected)
        val oin = ObjectInputStream(ByteArrayInputStream(out.toByteArray()))
        val actual = oin.readObject() as Vector2D
        TestCase.assertEquals(expected, actual)
        TestCase.assertEquals(expected, actual)
    }

    fun testGetDirection() {
        val start = Vector2D(1, 0)
        val end = Vector2D(3, 0)
        TestCase.assertEquals(Vector2D(1, 0), end.minus(start).normal())
    }

}
