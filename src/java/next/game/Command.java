/*
 * Created on Jul 31, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game;

import next.server.Request;
import next.server.Response;
import next.server.Session;

/**
 * @author a202490
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Command {

  boolean isTurn();

  void setRequest(Request request);

  Request getRequest();

  void setResponse(Response response);

  Response getResponse();

  void setSession(Session session);

  Session getSession();

  void setName(String name);

  String getName();

  void execute();
}