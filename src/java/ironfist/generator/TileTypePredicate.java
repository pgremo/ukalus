package ironfist.generator;

import ironfist.Tile;
import ironfist.util.Closure;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class TileTypePredicate implements Closure<Tile, Boolean> {

  private static final long serialVersionUID = 3834307341104395829L;
  private Class tileTypeClass;

  /**
   * @see com.threat.game.Predicate#invoke(Object)
   */
  public Boolean apply(Tile value) {
    return value != null && tileTypeClass.equals(value.getTileType()
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