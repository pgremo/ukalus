package ironfist.generator;

import ironfist.Tile;
import ironfist.Wall;

import java.util.Comparator;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class DungeonRoomComparator implements Comparator<Tile> {

  /**
   * @see java.util.Comparator#compare(Object, Object)
   */
  public int compare(Tile o1, Tile o2) {
    boolean result = true;

    if (o1 != null) {
      if (o2 == null) {
        result = false;
      } else {
        result = o1.getTileType() instanceof Wall
            && o2.getTileType() instanceof Wall;
      }
    }

    return result ? 0 : -1;
  }
}