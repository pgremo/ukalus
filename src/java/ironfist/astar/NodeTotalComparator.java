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
public class NodeTotalComparator implements Comparator<Node> {

  public int compare(Node o1, Node o2) {
    double f1 = o1.getTotal();
    double f2 = o2.getTotal();
    return f1 < f2 ? -1 : (f1 == f2 ? 0 : 1);
  }

}