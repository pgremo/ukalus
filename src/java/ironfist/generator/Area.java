package ironfist.generator;

import ironfist.Level;
import ironfist.Tile;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Area {

  private Random randomizer;
  private Vector2D coordinate;
  private List<Tile> list;

  {
    randomizer = new Random();
  }

  public Area(List<Tile> list) {
    this.list = list;
  }

  public Tile getRandom(Closure<Tile, Boolean> predicate) {
    Collections.shuffle(list, randomizer);
    Tile result = null;

    Iterator<Tile> iterator = list.iterator();
    while (iterator.hasNext() && result == null) {
      Tile current = iterator.next();
      if (predicate.apply(current)) {
        result = current;
      }
    }

    return result;
  }

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

  public Vector2D getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(Vector2D coordinate) {
    this.coordinate = coordinate;
  }

  public void place(Level level) {
    for (Tile current : list) {
      level.set(current.getCoordinate()
        .add(coordinate), current.getTileType());
    }
  }

  public boolean check(Closure<Tile, Boolean> comparator) {
    boolean result = true;
    Iterator<Tile> iterator = list.iterator();

    while (iterator.hasNext() && result) {
      result = comparator.apply(iterator.next());
    }

    return result;
  }

}