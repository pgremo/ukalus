package ironfist.level;

import ironfist.loop.Level;
import ironfist.math.Vector2D;

public interface Region {

  void place(Level level);

  int cost(Level level);

  Vector2D getLocation();

  void setLocation(Vector2D location);

}