package ironfist.ui.jcurses;

import ironfist.CreateCommand;
import ironfist.Creature;
import jcurses.event.ValueChangedEvent;
import jcurses.event.ValueChangedListener;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.Window;


/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Create implements ValueChangedListener {
  private InputField nameField;
  private Window nameWindow;

  /**
   * DOCUMENT ME!
   */
  public void run() {
    CharColor colors = new CharColor(CharColor.BLACK, CharColor.WHITE);
    nameWindow = new Window(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(), 
                            false, null);

    GridLayoutManager layout = new GridLayoutManager(2, 1);
    nameWindow.getRootPanel().setPanelColors(colors);
    nameWindow.getRootPanel().setLayoutManager(layout);
    nameWindow.setShadow(false);

    Label label = new Label("Enter Name:");
    label.setColors(colors);
    layout.addWidget(label, 0, 0, 1, 1, GridLayoutManager.ALIGNMENT_CENTER, 
                     GridLayoutManager.ALIGNMENT_RIGHT);

    nameField = new InputField(20);
    nameField.setTextComponentColors(colors);
    nameField.setDelimiter(' ');
    nameField.setDelimiterColors(colors);
    nameField.addListener(this);
    layout.addWidget(nameField, 1, 0, 1, 1, GridLayoutManager.ALIGNMENT_CENTER, 
                     GridLayoutManager.ALIGNMENT_LEFT);

    nameWindow.show();
  }

  /**
   * @see jcurses.event.ValueChangedListener#valueChanged(ValueChangedEvent)
   */
  public void valueChanged(ValueChangedEvent event) {
    Object source = event.getSource();

    if (nameField.equals(source) && nameField.isDone()) {
      String name = ((InputField) nameField).getText();
      nameWindow.close();

      Creature hero = (Creature) (new CreateCommand().execute(name));
      Game game = new Game(hero);
      game.run();
    }
  }
}