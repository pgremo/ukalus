/*
 * Created on Aug 3, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game.commands;


/**
 * @author a202490
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreateCharacterCommand extends AbstractCommand {
  /* (non-Javadoc)
   * @see next.game.Command#execute()
   */
  public void execute() {
    getSession().setAttribute("player", new Object());
  }
}
