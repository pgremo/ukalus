package ironfist.ui.jcurses;

import java.util.SortedMap;
import java.util.TreeMap;

import jcurses.event.ItemListener;
import jcurses.event.ItemListenerManager;
import jcurses.util.Rectangle;
import jcurses.widgets.Widget;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class IndexedMenuList extends Widget {

  private ItemListenerManager listenerManager;
  private String seperator;
  private SortedMap items;
  private char key;
  private int width;

  {
    listenerManager = new ItemListenerManager();
    seperator = " - ";
    items = new TreeMap();
    key = 'A';
  }

  /**
   * DOCUMENT ME!
   * 
   * @param item
   *          DOCUMENT ME!
   */
  public void add(String item) {
    while (Character.isLetter(key)
        && items.containsKey(Character.toString(key))) {
      key++;
    }

    add(Character.toString(key), item);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param index
   *          DOCUMENT ME!
   * @param item
   *          DOCUMENT ME!
   */
  public void add(String index, String item) {
    items.put(index, item);

    int length = index.length() + seperator.length() + item.length();

    if (length > width) {
      width = length;
    }
  }

  /**
   * @see jcurses.widgets.Widget#doPaint()
   */
  protected void doPaint() {
  }

  /**
   * @see jcurses.widgets.Widget#doRepaint()
   */
  protected void doRepaint() {
    doPaint();
  }

  /**
   * @see jcurses.widgets.Widget#getPreferredSize()
   */
  protected Rectangle getPreferredSize() {
    return null;
  }

  /**
   * @see jcurses.widgets.Widget#isFocusable()
   */
  protected boolean isFocusable() {
    return true;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param listener
   *          DOCUMENT ME!
   */
  public void addItemListener(ItemListener listener) {
    listenerManager.addListener(listener);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param listener
   *          DOCUMENT ME!
   */
  public void removeItemListener(ItemListener listener) {
    listenerManager.removeListener(listener);
  }

  /**
   * Returns the seperator.
   * 
   * @return String
   */
  public String getSeperator() {
    return seperator;
  }

  /**
   * Sets the seperator.
   * 
   * @param seperator
   *          The seperator to set
   */
  public void setSeperator(String value) {
    seperator = value;
    width = width + value.length() - seperator.length();
  }
}