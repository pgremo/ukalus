package ironfist.generator;

import ironfist.Tile;
import ironfist.geometry.Vector;
import ironfist.util.Predicate;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class TileTypeDirectionPredicate implements Predicate {
  private Class tileTypeClass;
  private Vector direction;
  private Area area;

  /**
   * @see com.threat.game.Predicate#allow(Object)
   */
  public boolean allow(Object value) {
    Tile tile = (Tile) value;

    return (tileTypeClass.equals(tile.getTileType().getClass()) && 
           (area.get(tile.getCoordinate().add(direction)) == null));
  }

  /**
   * Sets the direction.
   * 
   * @param direction The direction to set
   */
  public void setDirection(Vector direction) {
    this.direction = direction;
  }

  /**
   * Sets the area.
   * 
   * @param area The area to set
   */
  public void setArea(Area area) {
    this.area = area;
  }

  /**
   * Sets the tileTypeClass.
   * 
   * @param tileTypeClass The tileTypeClass to set
   */
  public void setTileTypeClass(Class tileTypeClass) {
    this.tileTypeClass = tileTypeClass;
  }
}