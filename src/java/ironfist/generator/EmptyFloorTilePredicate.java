package ironfist.generator;

import ironfist.Floor;
import ironfist.Tile;

/**
 * @author pmgremo
 * 
 */
public class EmptyFloorTilePredicate extends TileTypePredicate {

  private static final long serialVersionUID = 3256440322119905848L;

  public EmptyFloorTilePredicate(Class tileTypeClass) {
    super(tileTypeClass);
  }

  public Boolean apply(Tile value) {
    boolean result = super.apply(value);
    if ((value).getTileType() instanceof Floor) {
      Floor floor = (Floor) value.getTileType();
      result = result && floor.getDoor() == null;
      result = result && floor.getCreature() == null;
    }
    return result;
  }

}