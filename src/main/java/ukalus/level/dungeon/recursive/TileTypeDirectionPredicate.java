package ukalus.level.dungeon.recursive;

import ukalus.Floor;
import ukalus.Tile;
import ukalus.math.Vector2D;
import ukalus.util.Closure;

public class TileTypeDirectionPredicate implements Closure<Tile, Boolean> {

  private static final long serialVersionUID = 3257282513566185784L;
  private Class tileTypeClass;
  private Vector2D direction;
  private Area area;

  public TileTypeDirectionPredicate(Class tileTypeClass, Vector2D direction,
      Area area) {
    this.tileTypeClass = tileTypeClass;
    this.direction = direction;
    this.area = area;
  }

  public Boolean apply(Tile tile) {
    Tile floor = area.get(tile.getLocation()
      .minus(direction));
    return tileTypeClass.equals(tile.getTileType()
      .getClass()) && area.get(tile.getLocation()
      .plus(direction)) == null && floor != null
        && floor.getTileType() instanceof Floor;
  }

}