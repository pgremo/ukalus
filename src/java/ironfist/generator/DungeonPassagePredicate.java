package ironfist.generator;

import ironfist.Level;
import ironfist.Tile;
import ironfist.TileType;
import ironfist.Wall;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

public class DungeonPassagePredicate implements Closure<Tile, Boolean> {

  private static final long serialVersionUID = 3835150649460339001L;
  private Level level;

  public DungeonPassagePredicate(Level level) {
    this.level = level;
  }

  public Boolean apply(Tile o1) {
    boolean result = false;
    Vector2D location = o1.getCoordinate();
    if (level.contains(location)) {
      Tile tile = level.get(location);
      if (tile == null) {
        result = true;
      } else {
        TileType type = tile.getTileType();
        result = o1.getTileType() instanceof Wall && type instanceof Wall;
      }
    }
    return result;
  }
}