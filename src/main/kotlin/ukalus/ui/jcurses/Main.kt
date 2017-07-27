package ukalus.ui.jcurses

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
class Main : ItemListener {
    private var window: Window? = null
    private var select: MenuList? = null

    /**
     * @see jcurses.event.ActionListener.actionPerformed
     */
    override fun stateChanged(event: ItemEvent) {
        val source = event.source

        if (source == select) {
            val result = event.item

            if (QUIT == result) {
                quit()
            } else if (NEW == result) {
                create()
            } else if (LOAD == result) {
                load()
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private fun create() {
        Create().run()
    }

    /**
     * DOCUMENT ME!
     */
    private fun load() {
        Load().run()
    }

    /**
     * DOCUMENT ME!
     */
    private fun quit() {
        window!!.close()
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
        select!!.add(NEW)
        select!!.add(LOAD)
        select!!.add(QUIT)

        layout.addWidget(select, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                WidgetsConstants.ALIGNMENT_CENTER)
        window!!.show()
    }

    companion object {

        private val LOAD = "Load"
        private val NEW = "New"
        private val QUIT = "Quit"

        /**
         * DOCUMENT ME!

         * @param args
         * *          DOCUMENT ME!
         * *
         * *
         * @throws Exception
         * *           DOCUMENT ME!
         */
        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {
            val main = Main()
            main.run()
        }
    }
}