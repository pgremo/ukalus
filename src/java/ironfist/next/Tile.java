package ironfist.next;

import ironfist.geometry.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class Tile {
  private Level level;
  private Vector location;
  private Collection things;

  {
    things = new ArrayList();
  }

  public Tile(Level level, Vector location) {
    this.level = level;
    this.location = location;
  }

  /**
   * Returns the location.
   *
   * @return Vector
   */
  public Vector getLocation() {
    return location;
  }

  public void addThing(Thing thing) {
    things.add(thing);
    thing.setTile(this);
  }

  public void removeThing(Thing thing) {
    things.remove(thing);
    thing.setTile(null);
  }

  public boolean containsThing(Thing thing) {
    return things.contains(thing);
  }

  public Iterator getThings() {
    return things.iterator();
  }

  /**
   * Returns the level.
   *
   * @return Level
   */
  public Level getLevel() {
    return level;
  }
}
