package ironfist.ui.jcurses;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Marker {

  public static final Marker WALL = new Marker("wall");
  public static final Marker FLOOR = new Marker("passage");
  public static final Marker DOOR_OPEN = new Marker("door_open");
  public static final Marker DOOR_CLOSED = new Marker("door_closed");
  public static final Marker STAIRS_UP = new Marker("stairs_up");
  public static final Marker STAIRS_DOWN = new Marker("stairs_down");
  public static final Marker WEAPON = new Marker("weapon");
  public static final Marker HERO = new Marker("hero");
  private String name;

  private Marker(String name) {
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