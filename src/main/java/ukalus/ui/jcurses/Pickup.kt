package ukalus.ui.jcurses

import ukalus.Floor
import ukalus.Thing

import java.util.HashMap

import jcurses.event.ItemEvent
import jcurses.event.ItemListener
import jcurses.system.CharColor
import jcurses.system.Toolkit
import jcurses.widgets.Dialog
import jcurses.widgets.GridLayoutManager
import jcurses.widgets.MenuList
import jcurses.widgets.WidgetsConstants

/**
 * @author pmgremo
 */
class Pickup
/**
 * Creates a new Inventory object.

 * @param creature
 * *          DOCUMENT ME!
 */
(private val floor: Floor) : ItemListener {
    private var window: Dialog? = null
    private var menu: MenuList? = null
    private var things: MutableMap<Any, Any>? = null
    private var selected: Thing? = null

    init {
        things = HashMap<Any, Any>()
    }

    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    fun selectThing(): Thing? {
        val colors = CharColor(CharColor.BLACK, CharColor.WHITE)
        window = Dialog(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(),
                false, null)

        val layout = GridLayoutManager(1, 1)
        window!!.rootPanel.panelColors = colors
        window!!.rootPanel.layoutManager = layout
        window!!.setShadow(false)

        menu = MenuList()
        menu!!.addListener(this)
        menu!!.colors = colors
        menu!!.selectedItemColors = CharColor(CharColor.WHITE, CharColor.BLACK)

        layout.addWidget(menu, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                WidgetsConstants.ALIGNMENT_CENTER)

        things!!.clear()

        val iterator = floor.things

        while (iterator.hasNext()) {
            val current = iterator.next()
            things!!.put(current.toString(), current)
            menu!!.add(current.toString())
        }

        window!!.show()

        return selected
    }

    /**
     * @see jcurses.event.ItemListener.stateChanged
     */
    override fun stateChanged(event: ItemEvent) {
        if (menu == event.source && event.type == ItemEvent.CALLED) {
            selected = things!![event.item] as Thing
            window!!.close()
        }
    }

}