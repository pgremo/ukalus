/*
 * Created on Jul 31, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.server;

import java.util.HashMap;
import java.util.Map;


/**
 * @author a202490
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SessionManager {
  private int counter = 0;
  private Map sessions = new HashMap();

  public Session get(Request request, Response response) {
    String id = (String) request.getParameter("sessionId");
    Session result = (Session) sessions.get(id);

    if (result == null) {
      id = String.valueOf(counter++);
      result = new Session(id);
      sessions.put(id, result);
    }

    response.setSessionId(id);

    return result;
  }
}
