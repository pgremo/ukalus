package ironfist.generator;

import ironfist.Tile;
import ironfist.Wall;

import java.util.Comparator;


/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class DungeonRoomComparator implements Comparator {
  /**
   * @see java.util.Comparator#compare(Object, Object)
   */
  public int compare(Object o1, Object o2) {
    boolean result = true;

    if (o1 != null) {
      if (o2 == null) {
        result = false;
      } else {
        result = (((Tile) o1).getTileType() instanceof Wall && 
                 ((Tile) o2).getTileType() instanceof Wall);
      }
    }

    return result ? 0 : -1;
  }
}