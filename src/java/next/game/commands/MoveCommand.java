/*
 * Created on Aug 6, 2003
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
public class MoveCommand extends AbstractCommand {
  /* (non-Javadoc)
   * @see next.game.Command#execute()
   */
  public void execute() {
    Object mover = getRequest().getParameter("mover");
    Direction direction = (Direction) getRequest().getParameter("direction");
  }

  /* (non-Javadoc)
   * @see next.game.Command#isTurn()
   */
  public boolean isTurn() {
    return true;
  }
}
