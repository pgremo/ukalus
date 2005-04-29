package ironfist.graph;

import java.util.Vector;

public class BFSTraversal extends GraphTraversal {

  public BFSTraversal(Graph graph, GraphTraversalDelegate delegate) {
    super(graph, delegate);
  }

  public void start(Node root) {
    Vector<Node> queue = new Vector<Node>();
    queue.addElement(root);

    while (!queue.isEmpty()) {
      Node node = queue.elementAt(0);

      if (delegate.hasUnvisitedNeighbour(node)) {
        Edge edge = delegate.getUnvisitedNeighbour(node);
        delegate.traverse(edge);
        queue.addElement(edge.getTail());
      } else
        queue.removeElementAt(0);
    }
  }

}