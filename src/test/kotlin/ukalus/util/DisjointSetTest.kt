package ukalus.util

import junit.framework.TestCase
import java.util.*

class DisjointSetTest : TestCase() {

    fun testOperations() {
        val s = DisjointSet(ELEMENTS)

        var k = 1
        while (k < COUNT_IN_SET) {
            var j = 0
            while (j + k < ELEMENTS) {
                s.union(s.find(j), s.find(j + k))
                j += 2 * k
            }
            k *= 2
        }

        val actual = LinkedList<Int>()
        for (i in 0..ELEMENTS - 1) {
            actual.add(s.find(i))
            val item = s.find(i)
            if (i % COUNT_IN_SET == COUNT_IN_SET - 1) {
                TestCase.assertEquals(Array(COUNT_IN_SET) { item }.toList(), actual)
                actual.clear()
            }
        }
    }

    companion object {
        private val ELEMENTS = 128
        private val COUNT_IN_SET = 16
    }
}
