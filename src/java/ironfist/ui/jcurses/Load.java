package ironfist.ui.jcurses;

import ironfist.Creature;
import ironfist.Referee;
import ironfist.persistence.Persistence;
import jcurses.event.ActionEvent;
import jcurses.event.ItemEvent;
import jcurses.event.ItemListener;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.MenuList;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Load implements ItemListener {

  private MenuList select;
  private Window window;

  /**
   * @see jcurses.event.ActionListener#actionPerformed(ActionEvent)
   */
  public void stateChanged(ItemEvent event) {
    Object source = event.getSource();

    if (source.equals(select)) {
      String name = event.getItem()
        .toString();
      Creature hero = Referee.load(name);
      window.close();
      Game game = new Game(hero);
      game.run();
    }
  }

  /**
   * DOCUMENT ME!
   */
  public void run() {
    CharColor colors = new CharColor(CharColor.BLACK, CharColor.WHITE);
    window = new Window(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(),
      false, null);

    GridLayoutManager layout = new GridLayoutManager(1, 1);
    window.getRootPanel()
      .setPanelColors(colors);
    window.getRootPanel()
      .setLayoutManager(layout);
    window.setShadow(false);

    select = new MenuList();
    select.addListener(this);
    select.setColors(colors);
    select.setSelectedItemColors(new CharColor(CharColor.WHITE, CharColor.BLACK));

    String[] files = Persistence.list();

    for (int index = 0; index < files.length; index++) {
      select.add(files[index]);
    }

    layout.addWidget(select, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
        WidgetsConstants.ALIGNMENT_CENTER);

    window.show();
  }
}