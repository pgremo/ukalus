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
public class CommandProcessorManager {
  private static final CommandProcessor NON_TURN = new NonTurn();
  private CommandProcessor turn;

  public CommandProcessor get(Command command) {
    CommandProcessor result = null;

    if (command.isTurn()) {
      if ((turn == null) || turn.isDone()) {
      	Game game = (Game)command.getSession().getApplication();
        turn = new Turn(game);
      }

      result = turn;
    } else {
      result = NON_TURN;
    }

    return result;
  }
}
