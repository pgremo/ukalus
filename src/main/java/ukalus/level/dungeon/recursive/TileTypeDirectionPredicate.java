package ukalus.level.dungeon.recursive;

import ukalus.Floor;
import ukalus.Tile;
import ukalus.math.Vector2D;

import java.util.function.Function;
import java.util.function.Predicate;

public class TileTypeDirectionPredicate implements Predicate<Tile> {

  private Class tileTypeClass;
  private Vector2D direction;
  private Area area;

  public TileTypeDirectionPredicate(Class tileTypeClass, Vector2D direction,
      Area area) {
    this.tileTypeClass = tileTypeClass;
    this.direction = direction;
    this.area = area;
  }

  public boolean test(Tile tile) {
    Tile floor = area.get(tile.getLocation()
      .minus(direction));
    return tileTypeClass.equals(tile.getTileType()
      .getClass()) && area.get(tile.getLocation()
      .plus(direction)) == null && floor != null
        && floor.getTileType() instanceof Floor;
  }

}