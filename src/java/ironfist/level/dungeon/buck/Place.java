package ironfist.level.dungeon.buck;

import ironfist.math.Vector2D;
import ironfist.util.Closure;

public class Place implements Closure<Vector2D, Object> {

  private static final long serialVersionUID = 3833186930283459120L;
  private int[][] level;
  private Vector2D coordinate;

  public Place(int[][] level, Vector2D coordinate) {
    this.level = level;
    this.coordinate = coordinate;
  }

  public Object apply(Vector2D current) {
    Vector2D target = coordinate.add(current);
    level[target.getX()][target.getY()] = 2;
    return current;
  }

}
