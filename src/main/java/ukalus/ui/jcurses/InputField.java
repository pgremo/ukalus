package ukalus.ui.jcurses;

import jcurses.system.InputChar;
import jcurses.widgets.TextField;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class InputField extends TextField {

  private boolean done;

  /**
   * Constructor for InputField.
   */
  public InputField() {
    super();
  }

  /**
   * Constructor for InputField.
   * 
   * @param width
   */
  public InputField(int width) {
    super(width);
  }

  /**
   * Constructor for InputField.
   * 
   * @param width
   * @param text
   */
  public InputField(int width, String text) {
    super(width, text);
  }

  /**
   * @see jcurses.widgets.Widget#handleInput(InputChar)
   */
  protected boolean handleInput(InputChar inputChar) {
    int value = inputChar.getCode();

    boolean result = false;
    if (value == '\n') {
      done = true;
      setText(getText(), true);
      result = true;
    } else {
      result = super.handleInput(inputChar);
    }
    return result;
  }

  /**
   * Returns the done.
   * 
   * @return boolean
   */
  public boolean isDone() {
    return done;
  }
}