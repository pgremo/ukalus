package ironfist;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Door implements Serializable {

  private static final long serialVersionUID = 3256728394132960823L;
  private Lock lock;
  private boolean open;

  /**
   * Creates a new Door object.
   * 
   * @param lock
   *          DOCUMENT ME!
   * @param open
   *          DOCUMENT ME!
   */
  public Door(Lock lock, boolean open) {
    this.lock = lock;
    this.open = open;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Lock getLock() {
    return lock;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean isOpen() {
    return open;
  }

  /**
   * DOCUMENT ME!
   */
  public void open() {
    open = (lock == null) || !lock.isLocked();
  }

  /**
   * DOCUMENT ME!
   */
  public void close() {
    open = false;
  }
}