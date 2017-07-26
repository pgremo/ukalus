/*
 * Created on Feb 21, 2005
 *
 */
package ukalus.math

import java.util.HashMap

import junit.framework.TestCase

/**
 * @author pmgremo
 */
class VectorTest : TestCase() {

    fun testRotate() {
        val actual = Vector(1.0, 0.0).rotate(90.0)
        TestCase.assertEquals(0, actual.x.toInt())
        TestCase.assertEquals(-1, actual.y.toInt())
    }

    fun testFindInHashMap() {
        val map = HashMap<Vector, Any?>()
        map.put(Vector(1.0, 0.0), null)
        TestCase.assertTrue(map.containsKey(Vector(1.0, 0.0)))
    }

    fun testAdd() {
        var result = Vector(1.0, 0.0)
        val angle = Vector(1.0, 1.0)
        result = result.add(angle)
        TestCase.assertEquals(Vector(2.0, 1.0), result)
    }

    fun testSubtract() {
        var result = Vector(1.0, 0.0)
        val angle = Vector(1.0, 1.0)
        result = result.subtract(angle)
        TestCase.assertEquals(Vector(0.0, -1.0), result)
    }

}