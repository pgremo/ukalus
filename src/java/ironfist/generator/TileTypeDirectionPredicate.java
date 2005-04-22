package ironfist.generator;

import ironfist.Tile;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

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
    return tileTypeClass.equals(tile.getTileType()
      .getClass()) && (area.get(tile.getLocation()
      .add(direction)) == null);
  }

}