package ironfist.ui.jcurses;

import ironfist.Floor;
import ironfist.Thing;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jcurses.event.ItemEvent;
import jcurses.event.ItemListener;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.widgets.Dialog;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.MenuList;
import jcurses.widgets.WidgetsConstants;

/**
 * @author pmgremo
 *  
 */
public class Pickup implements ItemListener {

  private Floor floor;
  private Dialog window;
  private MenuList menu;
  private Map things;
  private Thing selected;

  {
    things = new HashMap();
  }

  /**
   * Creates a new Inventory object.
   * 
   * @param creature
   *          DOCUMENT ME!
   */
  public Pickup(Floor floor) {
    this.floor = floor;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Thing selectThing() {
    CharColor colors = new CharColor(CharColor.BLACK, CharColor.WHITE);
    window = new Dialog(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(),
      false, null);

    GridLayoutManager layout = new GridLayoutManager(1, 1);
    window.getRootPanel()
      .setPanelColors(colors);
    window.getRootPanel()
      .setLayoutManager(layout);
    window.setShadow(false);

    menu = new MenuList();
    menu.addListener(this);
    menu.setColors(colors);
    menu.setSelectedItemColors(new CharColor(CharColor.WHITE, CharColor.BLACK));

    layout.addWidget(menu, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
        WidgetsConstants.ALIGNMENT_CENTER);

    things.clear();

    Iterator iterator = floor.getThings();

    while (iterator.hasNext()) {
      Object current = iterator.next();
      things.put(current.toString(), current);
      menu.add(current.toString());
    }

    window.show();

    return selected;
  }

  /**
   * @see jcurses.event.ItemListener#stateChanged(ItemEvent)
   */
  public void stateChanged(ItemEvent event) {
    if (menu.equals(event.getSource()) && event.getType() == ItemEvent.CALLED) {
      selected = (Thing) things.get(event.getItem());
      window.close();
    }
  }

}