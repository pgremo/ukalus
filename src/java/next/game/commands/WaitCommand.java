/*
 * Created on Aug 1, 2003
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
public class WaitCommand extends AbstractCommand {

	/* (non-Javadoc)
	 * @see next.server.Command#execute(next.server.Request, next.server.Response, next.server.Session)
	 */
	public void execute() {
	}


	/* (non-Javadoc)
	 * @see next.game.Command#isTurn()
	 */
	public boolean isTurn() {
		return true;
	}

}
