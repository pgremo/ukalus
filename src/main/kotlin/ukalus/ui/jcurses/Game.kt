package ukalus.ui.jcurses

import ukalus.Creature
import jcurses.event.ActionEvent
import jcurses.event.ActionListener
import jcurses.system.CharColor
import jcurses.system.Toolkit
import jcurses.widgets.GridLayoutManager
import jcurses.widgets.WidgetsConstants
import jcurses.widgets.Window

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Game
/**
 * Creates a new Game object.

 * @param creature
 * *          DOCUMENT ME!
 */
(private val hero: Creature) : ActionListener {
    private var window = Window(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(), false, null)
    private var client = JCursesClient(hero)

    /**
     * DOCUMENT ME!
     */
    fun run() {
        window.rootPanel.panelColors = CharColor(CharColor.BLACK, CharColor.WHITE)
        window.rootPanel.layoutManager = GridLayoutManager(1, Toolkit.getScreenHeight())
        window.setShadow(false)

        hero.setClient(client)
        client.addActionListener(this)

        GridLayoutManager(1, Toolkit.getScreenHeight()).addWidget(client, 0, 2, 1, 1, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER)

        Thread(Runner(client, hero)).start()

        window.show()
    }

    /**
     * @see jcurses.event.ActionListener.actionPerformed
     */
    override fun actionPerformed(event: ActionEvent) {
        val source = event.source

        if (client == source) {
            window.tryToClose()
        }
    }
}