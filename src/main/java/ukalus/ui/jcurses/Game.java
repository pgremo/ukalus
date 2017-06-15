package ukalus.ui.jcurses;

import ukalus.Creature;
import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Game implements ActionListener {

  private Creature hero;
  private Window window;
  private JCursesClient client;
  private Runner runner;

  /**
   * Creates a new Game object.
   * 
   * @param creature
   *          DOCUMENT ME!
   */
  public Game(Creature creature) {
    hero = creature;
  }

  /**
   * DOCUMENT ME!
   */
  public void run() {
    CharColor colors = new CharColor(CharColor.BLACK, CharColor.WHITE);
    window = new Window(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(),
      false, null);

    GridLayoutManager layout = new GridLayoutManager(1,
      Toolkit.getScreenHeight());
    window.getRootPanel()
      .setPanelColors(colors);
    window.getRootPanel()
      .setLayoutManager(layout);
    window.setShadow(false);

    client = new JCursesClient(hero);
    hero.setClient(client);
    client.addActionListener(this);

    layout.addWidget(client, 0, 2, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
        WidgetsConstants.ALIGNMENT_CENTER);

    runner = new Runner(client, hero);
    new Thread(runner).start();

    window.show();
  }

  /**
   * @see jcurses.event.ActionListener#actionPerformed(ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    Object source = event.getSource();

    if (client.equals(source)) {
      window.tryToClose();
    }
  }
}