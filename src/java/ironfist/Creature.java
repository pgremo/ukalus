package ironfist;

import ironfist.math.Vector;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Creature implements Serializable {

  private static final RecursiveShadowCastingVision fov = new RecursiveShadowCastingVision();

  //	private static final BeamCastingVision fov = new BeamCastingVision();
  private Map properties;
  private String name;
  private Vector coordinate;
  private Client client;
  private boolean levelChanged;
  private Level level;
  private List things;
  private int power;

  {
    properties = new HashMap();
    things = new LinkedList();
  }

  /**
   * Creates a new Creature object.
   * 
   * @param client
   *          DOCUMENT ME!
   */
  public void setClient(Client client) {
    this.client = client;
  }

  /**
   * Returns the coordinate.
   * 
   * @return Coordinate
   */
  public Vector getCoordinate() {
    return coordinate;
  }

  /**
   * Sets the coordinate.
   * 
   * @param coordinate
   *          The coordinate to set
   */
  public void setCoordinate(Vector coordinate) {
    this.coordinate = coordinate;
  }

  /**
   * DOCUMENT ME!
   */
  public synchronized void activate() {
    if (levelChanged) {
      client.onLevelChange(level);
      levelChanged = false;
    }

    client.onVisionChange(getVision());

    power = 1;

    while (power > 0) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param command
   *          DOCUMENT ME!
   */
  public synchronized void executeCommand(Command command) {
    CommandType type = command.getType();

    if (CommandType.MOVE.equals(type)) {
      Referee.move(this, (Vector) command.getParameter());
    } else if (CommandType.OPEN.equals(type)) {
      Referee.open(this, (Door) command.getParameter());
    } else if (CommandType.CLOSE.equals(type)) {
      Referee.close(this, (Door) command.getParameter());
    } else if (CommandType.UP.equals(type)) {
      Referee.up(this);
    } else if (CommandType.DOWN.equals(type)) {
      Referee.down(this);
    } else if (CommandType.QUIT.equals(type)) {
      Referee.quit(this);
    } else if (CommandType.SAVE.equals(type)) {
      Referee.save(this);
    } else if (CommandType.PICKUP.equals(type)) {
      Referee.pickup(this, (Thing) command.getParameter());
    } else if (CommandType.DROP.equals(type)) {
      Referee.drop(this, (Thing) command.getParameter());
    }

    power--;
    notify();
  }

  /**
   * Returns the level.
   * 
   * @return Level
   */
  public Level getLevel() {
    return level;
  }

  /**
   * Sets the level.
   * 
   * @param level
   *          The level to set
   */
  public void setLevel(Level level) {
    this.level = level;
    levelChanged = true;
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
   * Sets the name.
   * 
   * @param value
   *          The name to set
   */
  public void setName(String value) {
    this.name = value;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Object getProperty(Object key) {
    return properties.get(key);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key
   *          DOCUMENT ME!
   * @param value
   *          DOCUMENT ME!
   */
  public void setProperty(Object key, Object value) {
    properties.put(key, value);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key
   *          DOCUMENT ME!
   */
  public void removeProperty(Object key) {
    properties.remove(key);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param thing
   *          DOCUMENT ME!
   */
  public void addThing(Thing thing) {
    things.add(thing);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param thing
   *          DOCUMENT ME!
   */
  public void removeThing(Thing thing) {
    things.remove(thing);
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Iterator getThings() {
    return things.iterator();
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public List getVision() {
    Vector coordinate = getCoordinate();
    Level level = getLevel();

    return fov.getSeen(level, (int) coordinate.getX(), (int) coordinate.getY(),
      3);
  }
}