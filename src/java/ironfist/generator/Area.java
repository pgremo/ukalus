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

  private static final Vector2D[] DIRECTIONS = {
      Vector2D.get(-1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(1, 0),
      Vector2D.get(0, -1)};

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

  public boolean check(Closure<Tile, Boolean> predicate) {
    boolean result = true;
    Iterator<Tile> iterator = list.iterator();

    while (iterator.hasNext() && result) {
      result = predicate.apply(iterator.next());
    }

    return result;
  }

  public Vector2D getSide(Vector2D coordinate) {
    Vector2D result = null;

    for (int index = 0; index < 4 && result == null; index++) {
      if (get(coordinate.add(DIRECTIONS[index])) == null) {
        result = DIRECTIONS[index];
      }
    }

    return result;
  }

}