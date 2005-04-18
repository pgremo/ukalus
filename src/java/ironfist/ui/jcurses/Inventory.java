package ironfist.ui.jcurses;

import ironfist.Creature;
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
import jcurses.widgets.Label;
import jcurses.widgets.MenuList;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Inventory implements ItemListener {

  private Creature creature;
  private Window window;
  private MenuList menu;
  private Map<String, Object> things;
  private Thing selected;

  {
    things = new HashMap<String, Object>();
  }

  /**
   * Creates a new Inventory object.
   * 
   * @param creature
   *          DOCUMENT ME!
   */
  public Inventory(Creature creature) {
    this.creature = creature;
  }

  /**
   * DOCUMENT ME!
   */
  public void show() {
    CharColor colors = new CharColor(CharColor.BLACK, CharColor.WHITE);
    window = new Dialog(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(),
      false, null);

    GridLayoutManager layout = new GridLayoutManager(1, 1);
    window.getRootPanel()
      .setPanelColors(colors);
    window.getRootPanel()
      .setLayoutManager(layout);
    window.setShadow(false);

    Iterator<Thing> iterator = creature.getThings();

    if (iterator.hasNext()) {
      menu = new MenuList();
      menu.addListener(this);
      menu.setColors(colors);
      menu.setSelectedItemColors(new CharColor(CharColor.WHITE, CharColor.BLACK));

      layout.addWidget(menu, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
          WidgetsConstants.ALIGNMENT_CENTER);

      while (iterator.hasNext()) {
        Object current = iterator.next();
        things.put(current.toString(), current);
        menu.add(current.toString());
      }
    } else {
      Label label = new Label("Inventory is empty.");
      layout.addWidget(label, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
          WidgetsConstants.ALIGNMENT_CENTER);
    }

    window.show();
  }

  /**
   * @see jcurses.event.ItemListener#stateChanged(ItemEvent)
   */
  public void stateChanged(ItemEvent event) {
    if (menu.equals(event.getSource()) && (event.getType() == ItemEvent.CALLED)) {
      selected = (Thing) things.get(event.getItem());
      window.close();
      things.clear();
      menu.clear();
    }
  }

  /**
   * Returns the selected.
   * 
   * @return Thing
   */
  public Thing getSelected() {
    return selected;
  }
}