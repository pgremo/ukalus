package ironfist;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Lock {
  private Key key;
  private boolean locked;

  /**
   * Creates a new Lock object.
   * 
   * @param key DOCUMENT ME!
   */
  public Lock(Key key) {
    this.key = key;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean unlock(Key key) {
    locked = !this.key.equals(key);

    return !locked;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean lock(Key key) {
    locked = this.key.equals(key);

    return locked;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean isLocked() {
    return locked;
  }
}