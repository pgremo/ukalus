/*
 * Created on Aug 1, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game.commands;

import next.game.Game;

import next.server.ApplicationException;

/**
 * @author a202490
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JoinGameCommand extends AbstractCommand {

  /*
   * (non-Javadoc)
   * 
   * @see next.server.Command#execute(next.server.Request, next.server.Response,
   *      next.server.Session)
   */
  public void execute() {
    Game game = (Game) getSession().getApplication();
    Object player = getSession().getAttribute("player");

    if (player == null) {
      getResponse().setError(new ApplicationException("player not set"));
    }

    game.getPlayers()
      .add(player);
  }

}