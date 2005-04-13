package ironfist.flood.floodfill;

import java.util.HashSet;
import java.util.Set;

public class FloodFill {

  public Set<Node> solve(int distance, Node start) {
    Set<Node> result = new HashSet<Node>();
    Set<Node> current = new HashSet<Node>();
    current.add(start);
    for (int i = 0; i <= distance; i++) {
      Set<Node> next = new HashSet<Node>();
      for (Node target : current) {
        for (Node child : target.getChildren()) {
          if (!result.contains(child) && !current.contains(child)) {
            next.add(child);
          }
        }
        target.setDistance(i);
        result.add(target);
      }
      current = next;
    }
    return result;
  }

}
