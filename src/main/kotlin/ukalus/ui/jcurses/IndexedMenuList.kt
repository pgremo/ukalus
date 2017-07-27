package ukalus.ui.jcurses

import java.util.SortedMap
import java.util.TreeMap

import jcurses.event.ItemListener
import jcurses.event.ItemListenerManager
import jcurses.util.Rectangle
import jcurses.widgets.Widget

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class IndexedMenuList : Widget() {

    private var listenerManager: ItemListenerManager? = null
    /**
     * Returns the seperator.

     * @return String
     */
    var seperator: String? = null
        private set
    private var items: SortedMap<String, String>? = null
    private var key: Char = ' '
    private var width: Int = 0

    init {
        listenerManager = ItemListenerManager()
        seperator = " - "
        items = TreeMap<String, String>()
        key = 'A'
    }

    /**
     * DOCUMENT ME!

     * @param item
     * *          DOCUMENT ME!
     */
    fun add(item: String) {
        while (Character.isLetter(key) && items!!.containsKey(Character.toString(key))) {
            key++
        }

        add(Character.toString(key), item)
    }

    /**
     * DOCUMENT ME!

     * @param index
     * *          DOCUMENT ME!
     * *
     * @param item
     * *          DOCUMENT ME!
     */
    fun add(index: String, item: String) {
        items!!.put(index, item)

        val length = index.length + seperator!!.length + item.length

        if (length > width) {
            width = length
        }
    }

    /**
     * @see jcurses.widgets.Widget.doPaint
     */
    override fun doPaint() {}

    /**
     * @see jcurses.widgets.Widget.doRepaint
     */
    override fun doRepaint() {
        doPaint()
    }

    /**
     * @see jcurses.widgets.Widget.getPreferredSize
     */
    override fun getPreferredSize(): Rectangle? {
        return null
    }

    /**
     * @see jcurses.widgets.Widget.isFocusable
     */
    override fun isFocusable(): Boolean {
        return true
    }

    /**
     * DOCUMENT ME!

     * @param listener
     * *          DOCUMENT ME!
     */
    fun addItemListener(listener: ItemListener) {
        listenerManager!!.addListener(listener)
    }

    /**
     * DOCUMENT ME!

     * @param listener
     * *          DOCUMENT ME!
     */
    fun removeItemListener(listener: ItemListener) {
        listenerManager!!.removeListener(listener)
    }

    /**
     * Sets the separator.

     * @param value
     * *          The separator to set
     */
    fun setSeparator(value: String) {
        seperator = value
        width = width + value.length - seperator!!.length
    }
}