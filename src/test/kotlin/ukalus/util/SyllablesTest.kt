package ukalus.util

import java.util.Arrays

import junit.framework.TestCase

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class SyllablesTest : TestCase() {

    fun testSplitComplexWord() {
        val expected = arrayOf("he", "gan", "shab", "but", "tesh")
        val syllables = syllables("heganshabbuttesh")
        val actual = syllables.toTypedArray()
        TestCase.assertTrue(Arrays.equals(expected, actual))
    }

    fun testSplitSingleConsonant() {
        val expected = arrayOf("me", "ku")
        val syllables = syllables("meku")
        val actual = syllables.toTypedArray()
        TestCase.assertTrue(Arrays.equals(expected, actual))
    }

    fun testSplitMultipleSingleConsonants() {
        val expected = arrayOf("ma", "sa", "tei", "pe")
        val syllables = syllables("masateipe")
        val actual = syllables.toTypedArray()
        TestCase.assertTrue(Arrays.equals(expected, actual))
    }

    fun testSplitDoubleConsonantsAtEnd() {
        val expected = arrayOf("matt")
        val syllables = syllables("matt")
        val actual = syllables.toTypedArray()
        TestCase.assertTrue(Arrays.equals(expected, actual))
    }

    fun testSplitDoubleConsonants() {
        val expected = arrayOf("ob", "re", "gon")
        val syllables = syllables("obregon")
        val actual = syllables.toTypedArray()
        TestCase.assertTrue(Arrays.equals(expected, actual))
    }

    fun testSplitTripleConsonants() {
        var expected = arrayOf("bol", "chun")
        var syllables = syllables("bolchun")
        var actual = syllables.toTypedArray()
        TestCase.assertTrue(Arrays.equals(expected, actual))

        expected = arrayOf("bol", "vlun")
        syllables = syllables("bolvlun")
        actual = syllables.toTypedArray()
        TestCase.assertTrue(Arrays.equals(expected, actual))
    }

    fun testSplitQuadrupleConsonants() {
        val expected = arrayOf("kalt", "glon")
        val syllables = syllables("kaltglon")
        val actual = syllables.toTypedArray()
        TestCase.assertTrue(Arrays.equals(expected, actual))
    }
}