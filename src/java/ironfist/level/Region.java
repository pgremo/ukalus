package ironfist.level;

import ironfist.math.Vector2D;

public interface Region {

  void place(int[][] level);

  int cost(int[][] cells);

  Vector2D getLocation();

  void setLocation(Vector2D location);

}