package ironfist.ui.jcurses;

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
public class Main implements ItemListener {

  private static final String LOAD = "Load";
  private static final String NEW = "New";
  private static final String QUIT = "Quit";
  private Window window;
  private MenuList select;

  /**
   * @see jcurses.event.ActionListener#actionPerformed(ActionEvent)
   */
  public void stateChanged(ItemEvent event) {
    Object source = event.getSource();

    if (source.equals(select)) {
      Object result = event.getItem();

      if (QUIT.equals(result)) {
        quit();
      } else if (NEW.equals(result)) {
        create();
      } else if (LOAD.equals(result)) {
        load();
      }
    }
  }

  /**
   * DOCUMENT ME!
   */
  private void create() {
    new Create().run();
  }

  /**
   * DOCUMENT ME!
   */
  private void load() {
    new Load().run();
  }

  /**
   * DOCUMENT ME!
   */
  private void quit() {
    window.close();
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
    select.add(NEW);
    select.add(LOAD);
    select.add(QUIT);

    layout.addWidget(select, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
        WidgetsConstants.ALIGNMENT_CENTER);
    window.show();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param args
   *          DOCUMENT ME!
   * 
   * @throws Exception
   *           DOCUMENT ME!
   */
  static public void main(String[] args) throws Exception {
    Main main = new Main();
    main.run();
  }
}