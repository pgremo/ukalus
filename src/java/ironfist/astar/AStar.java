/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.astar;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author gremopm
 * 
 */
public class AStar {

  private static final List<Node> EMPTY_LIST = new LinkedList<Node>();
  private static final NodeTotalComparator COMPARATOR = new NodeTotalComparator();

  public Iterator<Node> solve(Heuristic heuristic, Cost cost, Node start, Node stop) {
    Queue<Node> open = new PriorityQueue<Node>(4, COMPARATOR);
    List<Node> close = new LinkedList<Node>();
    LinkedList<Node> result = null;
    open.add(start);
    while (open.size() > 0 && result == null) {
      Node current = open.poll();
      if (current.equals(stop)) {
        result = new LinkedList<Node>();
        while (current != null) {
          result.addFirst(current);
          current = current.getParent();
        }
      } else {
        Node[] successors = current.getSuccessors();
        for (int i = 0; i < successors.length; i++) {
          int newCost = cost.calculate(current, successors[i]);
          Node openNode = get(open, successors[i]);
          if (openNode == null || openNode.getCost() > newCost) {
            Node closeNode = get(close, successors[i]);
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
    return result == null ? EMPTY_LIST.iterator() : result.iterator();
  }

  public Node get(Collection<Node> collection, Node o) {
    Node result = null;
    Iterator iterator = collection.iterator();
    while (iterator.hasNext() && result == null) {
      Object current = iterator.next();
      if (current.equals(o)) {
        result = (Node)current;
      }
    }
    return result;
  }

}