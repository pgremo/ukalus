package ironfist.next;

/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class Slot {
  public static final Slot HEAD = new Slot("head");
  public static final Slot NECK = new Slot("neck");
  public static final Slot BODY = new Slot("body");
  public static final Slot CLOAK = new Slot("cloak");
  public static final Slot BRACERS = new Slot("bracer");
  public static final Slot GLOVES = new Slot("gloves");
  public static final Slot BELT = new Slot("belt");
  public static final Slot RIGHT_HAND = new Slot("right hand");
  public static final Slot LEFT_HAND = new Slot("left hand");
  public static final Slot RIGHT_RING = new Slot("right ring");
  public static final Slot LEFT_RING = new Slot("left ring");
  public static final Slot FEET = new Slot("feet");
  private String name;

  private Slot(String name) {
    this.name = name;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return super.toString();
  }
}
