package ironfist.generator;

import ironfist.Level;
import ironfist.Tile;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

import java.util.ArrayList;
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
  private Vector2D coordinate;
  private List<Tile> list;

  {
    randomizer = new Random();
  }

  /**
   * Creates a new Area object.
   * 
   * @param list
   *          DOCUMENT ME!
   */
  public Area(List<Tile> list) {
    this.list = list;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param predicate
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Tile getRandom(Closure<Tile, Boolean> predicate) {
    Tile result = null;
    List<Tile> candidates = new ArrayList<Tile>(list.size());

    for (Tile current : list) {
      if (predicate.apply(current)) {
        candidates.add(current);
      }
    }

    if (candidates.size() > 0) {
      result = candidates.get(randomizer.nextInt(candidates.size()));
    }

    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param coordinate
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Tile get(Vector2D coordinate) {
    Tile result = null;
    Iterator<Tile> iterator = list.iterator();

    while (iterator.hasNext() && result == null) {
      Tile current = iterator.next();

      if (current.getCoordinate()
        .equals(coordinate)) {
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
  public Vector2D getCoordinate() {
    return coordinate;
  }

  /**
   * Sets the coordinate.
   * 
   * @param coordinate
   *          The coordinate to set
   */
  public void setCoordinate(Vector2D coordinate) {
    this.coordinate = coordinate;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param level
   *          DOCUMENT ME!
   */
  public void place(Level level) {
    for (Tile current : list) {
      level.set(current.getCoordinate()
        .add(coordinate), current.getTileType());
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param level
   *          DOCUMENT ME!
   * @param comparator
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean check(Level level, Comparator<Tile> comparator) {
    boolean result = true;
    Iterator<Tile> iterator = list.iterator();

    while (iterator.hasNext() && result) {
      Tile current = iterator.next();
      Vector2D currentCoordinate = current.getCoordinate()
        .add(coordinate);

      if ((currentCoordinate.getX() < 0)
          || (currentCoordinate.getX() > level.getHeight() - 1)
          || (currentCoordinate.getY() < 0)
          || (currentCoordinate.getY() > level.getWidth() - 1)) {
        result = false;
      } else {
        Tile tile = level.get(current.getCoordinate()
          .add(coordinate));
        result = comparator.compare(tile, current) == 0;
      }
    }

    return result;
  }
}