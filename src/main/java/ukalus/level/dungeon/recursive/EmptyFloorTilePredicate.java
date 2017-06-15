package ukalus.level.dungeon.recursive;

import ukalus.Floor;
import ukalus.Tile;
import ukalus.TileType;

/**
 * @author pmgremo
 * 
 */
public class EmptyFloorTilePredicate extends IsTileType {

  private static final long serialVersionUID = 3256440322119905848L;

  public EmptyFloorTilePredicate() {
    super(Floor.class);
  }

  public Boolean apply(Tile value) {
    boolean result = super.apply(value);
    TileType tileType = value.getTileType();
    if (result && tileType instanceof Floor) {
      Floor floor = (Floor) tileType;
      result = result && floor.getDoor() == null;
      result = result && floor.getCreature() == null;
    }
    return result;
  }

}