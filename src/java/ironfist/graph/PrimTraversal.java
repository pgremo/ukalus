package ironfist.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimTraversal extends GraphTraversal {

  private Random random;

  public PrimTraversal(Graph graph, GraphTraversalDelegate delegate,
      Random random) {
    super(graph, delegate);
    this.random = random;
  }

  public void start(Node root) {
    List<Node> shell = new ArrayList<Node>();
    shell.add(root);

    while (!shell.isEmpty()) {
      int i = random.nextInt(shell.size());
      Node node = shell.get(i);

      if (delegate.hasUnvisitedNeighbour(node)) {
        Edge edge = delegate.getUnvisitedNeighbour(node);
        delegate.traverse(edge);
        shell.add(edge.getTail());
      } else {
        shell.remove(i);
      }
    }
  }

}