/*
 * Created on Aug 1, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.game.commands;

import next.game.Command;

import next.server.Request;
import next.server.Response;
import next.server.Session;


/**
 * @author a202490
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractCommand implements Command {
  private Request request;
  private Response response;
  private Session session;
  private String name;

  /* (non-Javadoc)
   * @see next.game.Command#setRequest(next.server.Request)
   */
  public void setRequest(Request request) {
    this.request = request;
  }

  /* (non-Javadoc)
   * @see next.game.Command#getRequest()
   */
  public Request getRequest() {
    return request;
  }

  /* (non-Javadoc)
   * @see next.game.Command#setResponse(next.server.Response)
   */
  public void setResponse(Response response) {
    this.response = response;
  }

  /* (non-Javadoc)
   * @see next.game.Command#getResponse()
   */
  public Response getResponse() {
    return response;
  }

  /* (non-Javadoc)
   * @see next.game.Command#setSession(next.server.Session)
   */
  public void setSession(Session session) {
    this.session = session;
  }

  /* (non-Javadoc)
   * @see next.game.Command#getSession()
   */
  public Session getSession() {
    return session;
  }

  /* (non-Javadoc)
   * @see next.game.Command#setName()
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see next.game.Command#getName()
   */
  public String getName() {
    return name;
  }

  /* (non-Javadoc)
   * @see next.game.Command#execute()
   */
  abstract public void execute();

  /* (non-Javadoc)
   * @see next.game.Command#isTurn()
   */
  public boolean isTurn() {
    return false;
  }
}
