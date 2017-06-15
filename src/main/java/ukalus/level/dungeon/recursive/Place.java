package ukalus.level.dungeon.recursive;

import ukalus.Level;
import ukalus.Tile;
import ukalus.math.Vector2D;
import ukalus.util.Closure;

public class Place implements Closure<Tile, Object> {

  private static final long serialVersionUID = 3833186930283459120L;
  private Level level;
  private Vector2D coordinate;

  public Place(Level level, Vector2D coordinate) {
    this.level = level;
    this.coordinate = coordinate;
  }

  public Object apply(Tile current) {
    level.set(current.getLocation()
      .add(coordinate), current.getTileType());
    return null;
  }

}
