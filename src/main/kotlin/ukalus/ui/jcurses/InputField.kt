package ukalus.ui.jcurses

import jcurses.system.InputChar
import jcurses.widgets.TextField

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class InputField : TextField {

    /**
     * Returns the done.

     * @return boolean
     */
    var isDone: Boolean = false
        private set

    /**
     * Constructor for InputField.
     */
    constructor() : super() {}

    /**
     * Constructor for InputField.

     * @param width
     */
    constructor(width: Int) : super(width) {}

    /**
     * Constructor for InputField.

     * @param width
     * *
     * @param text
     */
    constructor(width: Int, text: String) : super(width, text) {}

    /**
     * @see jcurses.widgets.Widget.handleInput
     */
    override fun handleInput(inputChar: InputChar): Boolean {
        return if (inputChar.code.toChar() == '\n') {
            isDone = true
            setText(text, true)
            true
        } else {
            super.handleInput(inputChar)
        }
    }
}