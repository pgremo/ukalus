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
public class Session {
  private String id;
  private Application application;
  private Map attributes = new HashMap();

  public Session(String id) {
    this.id = id;
  }

  /**
   * @return
   */
  public String getId() {
    return id;
  }

  /**
   * @return
   */
  public Application getApplication() {
    return application;
  }

  /**
   * @param application
   */
  public void setApplication(Application application) {
    this.application = application;
  }

  public void setAttribute(Object key, Object value) {
    attributes.put(key, value);
  }

  public Object getAttribute(Object key) {
    return attributes.get(key);
  }
}
