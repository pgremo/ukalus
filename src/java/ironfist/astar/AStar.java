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

  public Node[] solve(Heuristic heuristic, Cost cost, Node start, Node stop) {
    PriorityQueue open = new PriorityQueue(new NodeFComparator());
    List close = new LinkedList();
    List result = Collections.EMPTY_LIST;

    open.add(start);
    while (open.size() > 0 && result == Collections.EMPTY_LIST) {
      Node current = (Node) open.removeFirst();
      if (current.equals(stop)) {
        result = new LinkedList();
        while (current != null) {
          result.add(current);
          current = current.getParent();
        }
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

          open.remove(successors[i]);
          close.remove(successors[i]);

          open.add(successors[i]);
        }
      }
      close.add(current);
    }
    return (Node[]) result.toArray(new Node[result.size()]);
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