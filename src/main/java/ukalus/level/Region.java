package ukalus.level;

import ukalus.math.Vector2D;

public interface Region<T> {


  void place(Vector2D location, Level<T> level);

  int cost(Vector2D location, Level<T> level);

}