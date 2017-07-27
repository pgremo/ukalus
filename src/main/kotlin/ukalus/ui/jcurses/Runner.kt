package ukalus.ui.jcurses

import ukalus.Creature
import ukalus.Referee

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Runner
/**
 * Creates a new Runner object.

 * @param client
 * *          DOCUMENT ME!
 * *
 * @param creature
 * *          DOCUMENT ME!
 */
(private val client: JCursesClient, private val creature: Creature) : Runnable {

    /**
     * @see java.lang.Runnable.run
     */
    override fun run() {
        Referee.run(creature)
        client.setGameEnd()
    }

    /**
     * DOCUMENT ME!
     */
    fun stop() {
        Referee.stop()
    }
}