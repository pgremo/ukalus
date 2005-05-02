package ironfist.graph;

import java.util.LinkedList;
import java.util.Queue;

public class BFTraversal extends GraphTraversal {

  public BFTraversal(Graph graph, GraphTraversalDelegate delegate) {
    super(graph, delegate);
  }

  public void start(Node root) {
    Queue<Node> queue = new LinkedList<Node>();
    queue.add(root);

    while (!queue.isEmpty()) {
      Edge edge = delegate.getUnvisitedNeighbour(queue.peek());

      if (edge != null) {
        delegate.traverse(edge);
        queue.add(edge.getTail());
      } else {
        queue.poll();
      }
    }
  }

}