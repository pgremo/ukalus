package ironfist.generator;

import ironfist.Level;
import ironfist.Tile;
import ironfist.geometry.Vector;
import ironfist.util.Predicate;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Area {
  private Random randomizer;
  private Vector coordinate;
  private List list;

  {
    randomizer = new Random();
  }

  /**
   * Creates a new Area object.
   * 
   * @param list DOCUMENT ME!
   */
  public Area(List list) {
    this.list = list;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param predicate DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Tile getRandom(Predicate predicate) {
    Tile result = null;
    Tile[] candidates = new Tile[list.size()];
    int count = 0;

    Iterator iterator = list.iterator();

    while (iterator.hasNext()) {
      Tile current = (Tile) iterator.next();

      if (predicate.allow(current)) {
        candidates[count++] = current;
      }
    }

    if (count > 0) {
      result = candidates[randomizer.nextInt(count)];
    }

    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param coordinate DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Tile get(Vector coordinate) {
    Tile result = null;
    Iterator iterator = list.iterator();

    while (iterator.hasNext() && (result == null)) {
      Tile current = (Tile) iterator.next();

      if (current.getCoordinate().equals(coordinate)) {
        result = current;
      }
    }

    return result;
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
   * @param coordinate The coordinate to set
   */
  public void setCoordinate(Vector coordinate) {
    this.coordinate = coordinate;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param level DOCUMENT ME!
   */
  public void place(Level level) {
    Iterator iterator = list.iterator();

    while (iterator.hasNext()) {
      Tile current = (Tile) iterator.next();
      level.set(current.getCoordinate().add(coordinate), current.getTileType());
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param level DOCUMENT ME!
   * @param comparator DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean check(Level level, Comparator comparator) {
    boolean result = true;
    Iterator iterator = list.iterator();

    while (iterator.hasNext() && result) {
      Tile current = (Tile) iterator.next();
      Vector currentCoordinate = current.getCoordinate().add(coordinate);

      if ((currentCoordinate.getX() < 0) || 
          (currentCoordinate.getX() > level.getHeight() - 1) || 
          (currentCoordinate.getY() < 0) || 
          (currentCoordinate.getY() > level.getWidth() - 1)) {
        result = false;
      } else {
        Tile tile = level.get(current.getCoordinate().add(coordinate));
        result = (comparator.compare(tile, current) == 0);
      }
    }

    return result;
  }
}