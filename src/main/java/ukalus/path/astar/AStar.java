package ukalus.path.astar;

import ukalus.util.IsEqual;
import ukalus.util.Loop;

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

  private static final NodeTotalComparator COMPARATOR = new NodeTotalComparator();

  public Iterator<Node> solve(Heuristic heuristic, Cost cost, Node start,
      Node stop) {
    if (start == null || stop == null) {
      throw new IllegalArgumentException();
    }
    
    Queue<Node> open = new PriorityQueue<Node>(1, COMPARATOR);
    List<Node> close = new LinkedList<Node>();
    Node current = start;
    open.add(start);
    while (!open.isEmpty() && !current.equals(stop)) {
      current = open.poll();
      for (Node successor : current.getSuccessors()) {
        int newCost = cost.calculate(current, successor);
        Node openNode = new Loop<Node>(open).detect(new IsEqual<Node>(successor));
        if (openNode == null || openNode.getCost() > newCost) {
          Node closeNode = new Loop<Node>(close).detect(new IsEqual<Node>(
            successor));
          if (closeNode == null || closeNode.getCost() > newCost) {
            successor.setCost(newCost);
            successor.setEstimate(heuristic.estimate(successor));
            open.remove(openNode);
            close.remove(closeNode);
            open.add(successor);
          }
        }
      }
      close.add(current);
    }

    LinkedList<Node> result = new LinkedList<Node>();
    while (current != null) {
      result.addFirst(current);
      current = current.getParent();
    }

    return result.iterator();
  }
}