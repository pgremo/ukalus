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
public class Request {

  private Map parameters = new HashMap();
  private String applicationName;
  private String sessionId;

  public Object getParameter(Object key) {
    return parameters.get(key);
  }

  public Object setParameter(Object key, Object value) {
    return parameters.put(key, value);
  }

  /**
   * @return
   */
  public String getApplicationName() {
    return applicationName;
  }

  /**
   * @param string
   */
  public void setApplicationName(String string) {
    applicationName = string;
  }

  /**
   * @return
   */
  public String getSessionId() {
    return sessionId;
  }

  /**
   * @param string
   */
  public void setSessionId(String string) {
    sessionId = string;
  }
}