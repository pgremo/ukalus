package ironfist;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class CommandType {

  public static final CommandType CLOSE = new CommandType("close");
  public static final CommandType DOWN = new CommandType("down");
  public static final CommandType DROP = new CommandType("drop");
  public static final CommandType MOVE = new CommandType("move");
  public static final CommandType OPEN = new CommandType("open");
  public static final CommandType PICKUP = new CommandType("pickup");
  public static final CommandType QUIT = new CommandType("quit");
  public static final CommandType SAVE = new CommandType("save");
  public static final CommandType UP = new CommandType("up");
  public static final CommandType WAIT = new CommandType("wait");
  public static final CommandType INVENTORY = new CommandType("inventory");
  private String name;

  /**
   * Creates a new Command object.
   * 
   * @param name
   *          DOCUMENT ME!
   */
  public CommandType(String name) {
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
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String toString() {
    return name;
  }
}