package ironfist.next;

/**
 * @author pmgremo
 */
public class ActionType {

  public static final ActionType READ = new ActionType("read");
  public static final ActionType WEAR = new ActionType("wear");
  private String name;

  private ActionType(String name) {
    this.name = name;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return name;
  }

  /**
   * Returns the name.
   * 
   * @return String
   */
  public String getName() {
    return name;
  }
}