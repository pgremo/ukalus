/*
 * Created on Jul 31, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game.commands;

import next.game.Game;


/**
 * @author a202490
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QuitCommand extends AbstractCommand {
  /* (non-Javadoc)
 * @see next.server.Command#execute(next.server.Request, next.server.Response, next.server.Session)
 */
  public void execute() {
    Game game = (Game) getSession().getApplication();
    Object player = getSession().getAttribute("player");
    game.getPlayers().remove(player);
    getResponse().setParameter("exit", Boolean.TRUE);
  }
}
