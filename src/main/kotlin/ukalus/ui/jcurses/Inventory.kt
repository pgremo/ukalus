package ukalus.ui.jcurses

import ukalus.Creature
import ukalus.Thing

import java.util.HashMap

import jcurses.event.ItemEvent
import jcurses.event.ItemListener
import jcurses.system.CharColor
import jcurses.system.Toolkit
import jcurses.widgets.Dialog
import jcurses.widgets.GridLayoutManager
import jcurses.widgets.Label
import jcurses.widgets.MenuList
import jcurses.widgets.WidgetsConstants
import jcurses.widgets.Window

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Inventory
/**
 * Creates a new Inventory object.

 * @param creature
 * *          DOCUMENT ME!
 */
(private val creature: Creature) : ItemListener {
    private var window: Window? = null
    private var menu: MenuList? = null
    private var things: MutableMap<String, Any>? = null
    /**
     * Returns the selected.

     * @return Thing
     */
    var selected: Thing? = null
        private set

    init {
        things = HashMap<String, Any>()
    }

    /**
     * DOCUMENT ME!
     */
    fun show() {
        val colors = CharColor(CharColor.BLACK, CharColor.WHITE)
        window = Dialog(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(),
                false, null)

        val layout = GridLayoutManager(1, 1)
        window!!.rootPanel.panelColors = colors
        window!!.rootPanel.layoutManager = layout
        window!!.setShadow(false)

        val iterator = creature.getThings()

        if (iterator.hasNext()) {
            menu = MenuList()
            menu!!.addListener(this)
            menu!!.colors = colors
            menu!!.selectedItemColors = CharColor(CharColor.WHITE, CharColor.BLACK)

            layout.addWidget(menu, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                    WidgetsConstants.ALIGNMENT_CENTER)

            while (iterator.hasNext()) {
                val current = iterator.next()
                things!!.put(current.toString(), current)
                menu!!.add(current.toString())
            }
        } else {
            val label = Label("Inventory is empty.")
            layout.addWidget(label, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                    WidgetsConstants.ALIGNMENT_CENTER)
        }

        window!!.show()
    }

    /**
     * @see jcurses.event.ItemListener.stateChanged
     */
    override fun stateChanged(event: ItemEvent) {
        if (menu == event.source && event.type == ItemEvent.CALLED) {
            selected = things!![event.item] as Thing
            window!!.close()
            things!!.clear()
            menu!!.clear()
        }
    }
}