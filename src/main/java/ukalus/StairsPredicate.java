package ukalus;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class StairsPredicate implements Predicate<Tile> {

  private String direction;

  /**
   * @see com.threat.game.Predicate#invoke(Object)
   */
  public boolean test(Tile value) {
    boolean result = false;
    if (value != null) {
      TileType type = value.getTileType();

      if (type instanceof Floor) {
        Portal portal = ((Floor) type).getPortal();

        if (portal instanceof Stairs) {
          if (((Stairs) portal).getDirection()
            .equals(direction)) {
            result = true;
          }
        }
      }
    }

    return result;
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