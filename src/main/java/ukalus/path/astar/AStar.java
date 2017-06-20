package ukalus.path.astar;

import java.util.*;

/**
 * @author gremopm
 */
public class AStar {

  public Iterator<Node> solve(Heuristic heuristic, Cost cost, Node start, Node stop) {
    if (start == null || stop == null) {
      throw new IllegalArgumentException();
    }

    Queue<Node> open = new PriorityQueue<>(1, new NodeTotalComparator());
    List<Node> close = new LinkedList<>();
    Node current = start;
    open.add(start);
    while (!open.isEmpty() && !current.equals(stop)) {
      current = open.poll();
      for (Node successor : current.getSuccessors()) {
        int newCost = cost.calculate(current, successor);
        Node openNode = open.stream().filter(successor::equals).findFirst().orElse(null);
        if (openNode == null || openNode.getCost() > newCost) {
          Node closeNode = close.stream().filter(successor::equals).findFirst().orElse(null);
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

    LinkedList<Node> result = new LinkedList<>();
    while (current != null) {
      result.addFirst(current);
      current = current.getParent();
    }

    return result.iterator();
  }
}