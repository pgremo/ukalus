/*
 * Created on Aug 1, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package next.server;

import next.game.Game;

import java.util.HashMap;
import java.util.Map;

/**
 * @author a202490
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApplicationManager {

  private Map applications = new HashMap();

  {
    Application application = new Game();
    applications.put("test", application);
  }

  public Application get(Request request, Response response) {
    String applicationName = (String) request.getApplicationName();

    return (Application) applications.get(applicationName);
  }
}