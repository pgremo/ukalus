package ukalus.level.dungeon.recursive;

import ukalus.Level;
import ukalus.Tile;
import ukalus.Wall;
import ukalus.math.Vector2D;
import ukalus.util.Closure;

public class DungeonRoomPredicate implements Closure<Tile, Boolean> {

  private static final long serialVersionUID = 3617287926447551285L;

  private Level level;
  private Vector2D location;

  public DungeonRoomPredicate(Level level, Vector2D location) {
    this.level = level;
    this.location = location;
  }

  public Boolean apply(Tile o1) {
    boolean result = false;
    Vector2D coordinate = o1.getLocation()
      .plus(location);
    if (level.contains(coordinate)) {
      Tile tile = level.get(coordinate);
      result = tile == null
          || (o1.getTileType() instanceof Wall && tile.getTileType() instanceof Wall);
    }
    return !result;
  }
}