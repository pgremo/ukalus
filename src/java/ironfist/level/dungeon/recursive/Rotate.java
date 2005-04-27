package ironfist.level.dungeon.recursive;

import ironfist.Tile;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

public class Rotate implements Closure<Tile, Object> {
  private static final long serialVersionUID = 3978984375394578998L;
  private Vector2D direction;

  public Rotate(Vector2D direction) {
    this.direction = direction;
  }

  public Object apply(Tile current) {
    current.setLocation(current.getLocation()
      .rotate(direction));
    return null;
  }

}
