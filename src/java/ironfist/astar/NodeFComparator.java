/*
 * Created on Feb 11, 2005
 *
 */
package ironfist.astar;

import java.util.Comparator;

/**
 * @author gremopm
 *  
 */
public class NodeFComparator implements Comparator {

  /**
   *  
   */

  public int compare(Object o1, Object o2) {
    double f1 = ((Node) o1).getF();
    double f2 = ((Node) o2).getF();
    return f1 < f2 ? -1 : (f1 == f2 ? 0 : 1);
  }

}