/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.path.astar;

import ironfist.path.astar.Cost;
import ironfist.path.astar.Node;

/**
 * @author gremopm
 *  
 */
public class FixedCost implements Cost {

  private int cost;

  public FixedCost(int cost) {
    this.cost = cost;
  }

  public int calculate(Node previous, Node current) {
    int result = 1;
    Node next = previous.getParent();
    while (next != null) {
      result++;
      next = next.getParent();
    }
    return result * cost;
  }

}