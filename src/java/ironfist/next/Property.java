package ironfist.next;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Property {

  public static final Property IDENTIFIER = new Property("identifier");
  public static final Property IDENTIFIERS = new Property("identifiers");
  public static final Property DESCRIPTION = new Property("description");
  public static final Property INVENTORY = new Property("inventory");
  private String name;

  private Property(String name) {
    this.name = name;
  }

  /**
   * Returns the name.
   * 
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return name;
  }
}