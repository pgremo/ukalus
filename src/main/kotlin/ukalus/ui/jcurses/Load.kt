package ukalus.ui.jcurses

import ukalus.Creature
import ukalus.Referee
import ukalus.persistence.Persistence
import jcurses.event.ItemEvent
import jcurses.event.ItemListener
import jcurses.system.CharColor
import jcurses.system.Toolkit
import jcurses.widgets.GridLayoutManager
import jcurses.widgets.MenuList
import jcurses.widgets.WidgetsConstants
import jcurses.widgets.Window

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Load : ItemListener {

    private var select: MenuList? = null
    private var window: Window? = null

    override fun stateChanged(event: ItemEvent) {
        val source = event.source

        if (source == select) {
            val name = event.item
                    .toString()
            val hero = Referee.load(name)
            window!!.close()
            val game = Game(hero)
            game.run()
        }
    }

    /**
     * DOCUMENT ME!
     */
    fun run() {
        val colors = CharColor(CharColor.BLACK, CharColor.WHITE)
        window = Window(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(),
                false, null)

        val layout = GridLayoutManager(1, 1)
        window!!.rootPanel.panelColors = colors
        window!!.rootPanel.layoutManager = layout
        window!!.setShadow(false)

        select = MenuList()
        select!!.addListener(this)
        select!!.colors = colors
        select!!.selectedItemColors = CharColor(CharColor.WHITE, CharColor.BLACK)

        val files = Persistence.list()

        for (file in files!!) {
            select!!.add(file)
        }

        layout.addWidget(select, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                WidgetsConstants.ALIGNMENT_CENTER)

        window!!.show()
    }
}