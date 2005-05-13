package ironfist.level.maze.sparcecursal;

import ironfist.graph.Node;
import ironfist.graph.NodeCollection;

public class VisitedLast implements NodeCollection {

  private Node last;

  public void add(Node e) {
    last = e;
  }

  public Node get() {
    return last;
  }

  public void remove() {
    last = null;
  }

  public boolean isEmpty() {
    return last == null;
  }

}
