/*
 * Created on Aug 1, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game;

import next.server.Application;
import next.server.Request;
import next.server.Response;
import next.server.Session;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author a202490
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Game implements Application {

  private CommandManager commandManager = new CommandManager();
  private CommandProcessorManager processorManager = new CommandProcessorManager();
  private Collection players = new ArrayList();

  /*
   * (non-Javadoc)
   * 
   * @see next.server.Application#process(next.server.Command)
   */
  public void service(Request request, Response response, Session session) {
    Command command = commandManager.get(request, response, session);
    CommandProcessor processor = processorManager.get(command);
    processor.process(command);
  }

  /**
   * @return
   */
  public Collection getPlayers() {
    return players;
  }

  /**
   * @param collection
   */
  public void setPlayers(Collection collection) {
    players = collection;
  }
}