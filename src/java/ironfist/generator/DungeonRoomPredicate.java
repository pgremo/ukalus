package ironfist.generator;

import ironfist.Level;
import ironfist.Tile;
import ironfist.Wall;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
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
    Vector2D coordinate = o1.getLocation().add(location);
    if (level.contains(coordinate)) {
      Tile tile = level.get(coordinate);
      if (tile == null) {
        result = true;
      } else {
        result = o1.getTileType() instanceof Wall
            && tile.getTileType() instanceof Wall;
      }
    }
    return result;
  }
}