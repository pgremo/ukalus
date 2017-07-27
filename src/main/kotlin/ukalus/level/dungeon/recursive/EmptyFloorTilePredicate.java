package ukalus.level.dungeon.recursive;

import ukalus.Floor;
import ukalus.Tile;
import ukalus.TileType;

/**
 * @author pmgremo
 * 
 */
public class EmptyFloorTilePredicate extends IsTileType {

  public EmptyFloorTilePredicate() {
    super(Floor.class);
  }

  public boolean test(Tile value) {
    boolean result = super.test(value);
    TileType tileType = value.getTileType();
    if (result && tileType instanceof Floor) {
      Floor floor = (Floor) tileType;
      result = result && floor.getDoor() == null;
      result = result && floor.getCreature() == null;
    }
    return result;
  }

}