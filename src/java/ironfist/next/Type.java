package ironfist.next;

/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class Type {
  public static final Type ITEM = new Type("item");
  public static final Type CREATURE = new Type("creature");
  public static final Type FEATURE = new Type("feature");
  private String name;

  private Type(String name) {
    this.name = name;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return name;
  }
}
