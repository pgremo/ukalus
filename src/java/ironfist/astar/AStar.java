/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.astar;

import ironfist.util.PriorityQueue;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author gremopm
 *  
 */
public class AStar {

  public Iterator solve(Heuristic heuristic, Cost cost, Node start, Node stop) {
    PriorityQueue open = new PriorityQueue(new NodeFComparator());
    List close = new LinkedList();
    Iterator result = null;

    open.add(start);
    while (open.size() > 0 && result == null) {
      Node current = (Node) open.removeFirst();
      if (current.equals(stop)) {
        result = new NodeIterator(current);
      } else {
        Node[] successors = current.getSuccessors();
        for (int i = 0; i < successors.length; i++) {
          int newCost = cost.calculate(current, successors[i]);

          Node openNode = getNode(open, successors[i]);

          if (openNode != null && openNode.getG() <= newCost) {
            continue;
          }

          Node closeNode = getNode(close, successors[i]);

          if (closeNode != null && closeNode.getG() <= newCost) {
            continue;
          }

          successors[i].setG(newCost);
          successors[i].setH(heuristic.estimate(successors[i]));

          open.remove(openNode);
          close.remove(closeNode);

          open.add(successors[i]);
        }
      }
      close.add(current);
    }
    if (result == null) {
      result = Collections.EMPTY_LIST.iterator();
    }
    return result;
  }

  private Node getNode(Collection collection, Node successor) {
    Node result = null;
    Iterator iterator = collection.iterator();
    while (iterator.hasNext() && result == null) {
      Node node = (Node) iterator.next();
      if (node.equals(successor)) {
        result = node;
      }
    }
    return result;
  }
}