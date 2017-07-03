package ukalus.level.dungeon.recursive;

import ukalus.Level;
import ukalus.Tile;
import ukalus.math.Vector2D;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Area {

  private static final Vector2D[] DIRECTIONS = {
    new Vector2D(-1, 0),
    new Vector2D(0, 1),
    new Vector2D(1, 0),
    new Vector2D(0, -1)};

  private Vector2D coordinate;
  private List<Tile> list;

  public Area(List<Tile> list) {
    this.list = list;
  }

  public Tile getRandom(Predicate<Tile> predicate, Random randomizer) {
    Collections.shuffle(list, randomizer);
    return list.stream().filter(predicate).findFirst().orElse(null);
  }

  public Tile get(Vector2D coordinate) {
    return list.stream().filter(new MatchesVector(coordinate)).findFirst().orElse(null);
  }

  public Vector2D getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(Vector2D coordinate) {
    this.coordinate = coordinate;
  }

  public void place(Level level) {
    list.forEach(new Place(level, coordinate));
  }

  public boolean check(Predicate<Tile> predicate) {
    return list.stream().noneMatch(predicate);
  }

  public Vector2D getSide(Vector2D coordinate) {
    Vector2D result = null;

    for (int index = 0; index < 4 && result == null; index++) {
      if (get(coordinate.plus(DIRECTIONS[index])) == null) {
        result = DIRECTIONS[index];
      }
    }

    return result;
  }

  public void rotate(Vector2D direction) {
    list.forEach(new Rotate(direction));
  }

  public String toString() {
    return coordinate + "=" + list;
  }

}