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
    private var window: Window? = null
    private var client: JCursesClient? = null

    /**
     * DOCUMENT ME!
     */
    fun run() {
        val colors = CharColor(CharColor.BLACK, CharColor.WHITE)
        window = Window(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(),
                false, null)

        val layout = GridLayoutManager(1,
                Toolkit.getScreenHeight())
        window!!.rootPanel.panelColors = colors
        window!!.rootPanel.layoutManager = layout
        window!!.setShadow(false)

        client = JCursesClient(hero)
        hero.setClient(client)
        client!!.addActionListener(this)

        layout.addWidget(client, 0, 2, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                WidgetsConstants.ALIGNMENT_CENTER)

        val runner = Runner(client!!, hero)
        Thread(runner).start()

        window!!.show()
    }

    /**
     * @see jcurses.event.ActionListener.actionPerformed
     */
    override fun actionPerformed(event: ActionEvent) {
        val source = event.source

        if (client == source) {
            window!!.tryToClose()
        }
    }
}