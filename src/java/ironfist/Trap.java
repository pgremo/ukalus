package ironfist;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Trap {
  private boolean set;

  {
    set = true;
  }

  /**
   * DOCUMENT ME!
   */
  public void activate() {
    deactivate();
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public boolean isSet() {
    return set;
  }

  /**
   * DOCUMENT ME!
   */
  public void deactivate() {
    set = false;
  }
}