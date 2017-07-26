package ukalus.ui.jcurses

import ukalus.CreateCommand
import ukalus.Creature
import jcurses.event.ValueChangedEvent
import jcurses.event.ValueChangedListener
import jcurses.system.CharColor
import jcurses.system.Toolkit
import jcurses.widgets.GridLayoutManager
import jcurses.widgets.Label
import jcurses.widgets.WidgetsConstants
import jcurses.widgets.Window

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Create : ValueChangedListener {

    private var nameField: InputField? = null
    private var nameWindow: Window? = null

    /**
     * DOCUMENT ME!
     */
    fun run() {
        val colors = CharColor(CharColor.BLACK, CharColor.WHITE)
        nameWindow = Window(Toolkit.getScreenWidth(),
                Toolkit.getScreenHeight(), false, null)

        val layout = GridLayoutManager(2, 1)
        nameWindow!!.rootPanel.panelColors = colors
        nameWindow!!.rootPanel.layoutManager = layout
        nameWindow!!.setShadow(false)

        val label = Label("Enter Name:")
        label.colors = colors
        layout.addWidget(label, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                WidgetsConstants.ALIGNMENT_RIGHT)

        nameField = InputField(20)
        nameField!!.setTextComponentColors(colors)
        nameField!!.setDelimiter(' ')
        nameField!!.delimiterColors = colors
        nameField!!.addListener(this)
        layout.addWidget(nameField, 1, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                WidgetsConstants.ALIGNMENT_LEFT)

        nameWindow!!.show()
    }

    /**
     * @see jcurses.event.ValueChangedListener.valueChanged
     */
    override fun valueChanged(event: ValueChangedEvent) {
        val source = event.source

        if (nameField == source && nameField!!.isDone) {
            val name = nameField!!.text
            nameWindow!!.close()

            val hero = CreateCommand().execute(name) as Creature
            val game = Game(hero)
            game.run()
        }
    }
}