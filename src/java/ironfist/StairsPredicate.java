package ironfist;

import ironfist.util.Predicate;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class StairsPredicate implements Predicate {

  private String direction;

  /**
   * @see com.threat.game.Predicate#allow(Object)
   */
  public boolean allow(Object value) {
    if (value != null) {
      TileType type = ((Tile) value).getTileType();

      if (type instanceof Floor) {
        Portal portal = ((Floor) type).getPortal();

        if (portal instanceof Stairs) {
          if (((Stairs) portal).getDirection()
            .equals(direction)) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /**
   * Returns the direction.
   * 
   * @return String
   */
  public String getDirection() {
    return direction;
  }

  /**
   * Sets the direction.
   * 
   * @param direction
   *          The direction to set
   */
  public void setDirection(String direction) {
    this.direction = direction;
  }
}