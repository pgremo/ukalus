package ironfist.flood.floodfill;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FloodFill {

  public Set<Node> getTemplate(int distance, Node start) {
    Set<Node> result = new HashSet<Node>();
    Queue<Node> open = new LinkedList<Node>();
    open.add(start);
    while (open.size() > 0) {
      Node current = open.remove();
      for (Node child : current.getChildren()) {
        if (child.getDistance() <= distance && !open.contains(child)) {
          open.add(child);
        }
      }
      result.add(current);
    }
    return result;
  }

}
