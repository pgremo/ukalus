package ironfist.generator;

import ironfist.Floor;
import ironfist.Tile;

/**
 * @author pmgremo
 *
 */
public class EmptyFloorTilePredicate extends TileTypePredicate {

	/**
	 * @see com.threat.game.Predicate#allow(Object)
	 */
	public boolean allow(Object value) {
		boolean result = super.allow(value);
		if (((Tile)value).getTileType() instanceof Floor){
			Floor floor = (Floor)((Tile)value).getTileType();
			result = result && floor.getDoor() == null;
			result = result && floor.getCreature() == null;
		}
		return result;
	}

}
