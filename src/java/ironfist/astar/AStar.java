/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.astar;

import ironfist.util.PriorityQueue;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author gremopm
 *  
 */
public class AStar {

  private Cost cost;
  private Heuristic heuristic;

  public AStar(Cost cost, Heuristic heuristic) {
    this.cost = cost;
    this.heuristic = heuristic;
  }

  public Node[] solve(Node start, Node stop) {
    PriorityQueue open = new PriorityQueue(100, new NodeFComparator());
    List close = new LinkedList();
    List result = Collections.EMPTY_LIST;

    open.add(start);
    while (open.size() > 0 && result == null) {
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
          int newCost = successors[i].getG()
              + cost.calculate(current, successors[i]);

          Node openNode = null;
          Iterator iterator = open.iterator();
          while (iterator.hasNext() && openNode == null) {
            Node node = (Node) iterator.next();
            if (node.equals(successors[i])) {
              openNode = node;
            }
          }

          if (openNode != null && openNode.getG() <= newCost) {
            continue;
          }

          Node closeNode = null;
          iterator = open.iterator();
          while (iterator.hasNext() && openNode == null) {
            Node node = (Node) iterator.next();
            if (node.equals(successors[i])) {
              closeNode = node;
            }
          }

          if (openNode != null && closeNode.getG() <= newCost) {
            continue;
          }

          successors[i].setG(newCost);
          successors[i].setH(heuristic.estimate(successors[i], stop));

          open.remove(successors[i]);
          close.remove(successors[i]);

          open.add(successors[i]);
        }
      }
      close.add(current);
    }
    return (Node[]) result.toArray(new Node[result.size()]);
  }
}