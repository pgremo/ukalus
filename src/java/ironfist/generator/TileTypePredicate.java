package ironfist.generator;

import ironfist.Tile;
import ironfist.util.Predicate;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class TileTypePredicate implements Predicate {

  private Class tileTypeClass;

  /**
   * @see com.threat.game.Predicate#allow(Object)
   */
  public boolean allow(Object value) {
    return value != null && tileTypeClass.equals(((Tile) value).getTileType()
      .getClass());
  }

  /**
   * Sets the tileTypeClass.
   * 
   * @param tileTypeClass
   *          The tileTypeClass to set
   */
  public void setTileTypeClass(Class tileTypeClass) {
    this.tileTypeClass = tileTypeClass;
  }
}