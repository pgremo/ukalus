/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.astar;

import ironfist.util.Collections;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author gremopm
 *  
 */
public class AStar {

  private static final NodeTotalComparator COMPARATOR = new NodeTotalComparator();

  public Iterator solve(Heuristic heuristic, Cost cost, Node start, Node stop) {
    OpenQueue open = new OpenQueue(COMPARATOR);
    CloseList close = new CloseList();
    LinkedList result = null;
    open.add(start);
    while (open.size() > 0 && result == null) {
      Node current = (Node) open.removeFirst();
      if (current.equals(stop)) {
        result = new LinkedList();
        while (current != null) {
          result.addFirst(current);
          current = current.getParent();
        }
      } else {
        Node[] successors = current.getSuccessors();
        for (int i = 0; i < successors.length; i++) {
          int newCost = cost.calculate(current, successors[i]);
          Node openNode = (Node) open.get(successors[i]);
          if (openNode == null || openNode.getCost() > newCost) {
            Node closeNode = (Node) close.get(successors[i]);
            if (closeNode == null || closeNode.getCost() > newCost) {
              successors[i].setCost(newCost);
              successors[i].setEstimate(heuristic.estimate(successors[i]));
              open.remove(openNode);
              close.remove(closeNode);
              open.add(successors[i]);
            }
          }
        }
      }
      close.add(current);
    }
    return result == null ? Collections.EMPTY_ITERATOR : result.iterator();
  }

}