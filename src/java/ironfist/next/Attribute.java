package ironfist.next;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Attribute {

  public static final Attribute PROTECTION = new Attribute("protection");
  public static final Attribute SPEED = new Attribute("speed");
  private String name;

  private Attribute(String name) {
    this.name = name;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return name;
  }
}