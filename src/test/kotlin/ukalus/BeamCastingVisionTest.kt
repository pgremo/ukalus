package ukalus

import org.junit.After
import org.junit.Before
import org.junit.Test
import ukalus.persistence.Persistence

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class BeamCastingVisionTest {

    private var creature: Creature? = null

    /**
     * DOCUMENT ME!
     */
    @Test
    fun testVision() {
        val vision = creature!!.vision
        assertNotNull(vision)
        assertTrue(vision.size > 0)
        for (current in vision) {
            assertNotNull(current)
        }
    }

    /**
     * @see junit.framework.TestCase.setUp
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        creature = CreateCommand().execute("ja") as Creature
    }

    /**
     * @see junit.framework.TestCase.tearDown
     */
    @After
    @Throws(Exception::class)
    fun tearDown() {
        Persistence.delete("ja")
        Referee.quit(creature)
    }
}