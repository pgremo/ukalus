/*
 * Created on Aug 2, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game;


/**
 * @author a202490
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NonTurn implements CommandProcessor {
  /* (non-Javadoc)
   * @see next.game.CommandProcessor#process(next.game.Command)
   */
  public void process(Command command) {
    command.execute();
  }

  /* (non-Javadoc)
   * @see next.game.CommandProcessor#isDone()
   */
  public boolean isDone() {
    return false;
  }
}
