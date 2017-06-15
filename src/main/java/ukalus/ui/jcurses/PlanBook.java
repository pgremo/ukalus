package ukalus.ui.jcurses;

import ukalus.persistence.Persistence;
import ukalus.persistence.PersistenceException;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class PlanBook implements Serializable {

  private static final long serialVersionUID = 3690195434467308848L;
  private String name;
  private Object page;

  /**
   * DOCUMENT ME!
   * 
   * @param key
   *          DOCUMENT ME!
   */
  public void turnTo(String key) {
    if ((name != null) && !name.equals(key)) {
      try {
        Persistence.put(name, page);
      } catch (PersistenceException e) {
        e.printStackTrace();
      }
    }

    name = key;

    try {
      page = Persistence.get(name);
    } catch (PersistenceException e) {
      e.printStackTrace();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Object get() {
    return page;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key
   *          DOCUMENT ME!
   * @param page
   *          DOCUMENT ME!
   */
  public void add(String key, Object page) {
    turnTo(key);
    this.page = page;
  }
}