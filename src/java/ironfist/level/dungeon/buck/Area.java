package ironfist.level.dungeon.buck;

import ironfist.math.Vector2D;
import ironfist.util.Closure;
import ironfist.util.Loop;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Area {

  protected static final Vector2D[] DIRECTIONS = {
      Vector2D.get(-1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(1, 0),
      Vector2D.get(0, -1)};

  private Vector2D coordinate;
  private List<Vector2D> list;

  public Area(List<Vector2D> list) {
    this.list = list;
  }

  public Vector2D getRandom(Closure<Vector2D, Boolean> predicate,
      Random randomizer) {
    Collections.shuffle(list, randomizer);
    return new Loop<Vector2D>(list).detect(predicate);
  }

  public Vector2D get(Vector2D coordinate) {
    return new Loop<Vector2D>(list).detect(new MatchesVector(coordinate));
  }

  public Vector2D getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(Vector2D coordinate) {
    this.coordinate = coordinate;
  }

  public void place(int[][] level) {
    new Loop<Vector2D>(list).forEach(new Place(level, coordinate));
  }

  public boolean check(Closure<Vector2D, Boolean> predicate) {
    return new Loop<Vector2D>(list).detect(predicate) == null;
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

  public String toString() {
    return coordinate + "=" + list;
  }

}