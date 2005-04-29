package ironfist.graph;

import java.util.Vector;

public class DFSTraversal extends GraphTraversal {

  public DFSTraversal(Graph graph, GraphTraversalDelegate delegate) {
    super(graph, delegate);
  }

  public void start(Node root) {
    Vector<Node> stack = new Vector<Node>();
    stack.addElement(root);

    while (!stack.isEmpty()) {
      Node node = stack.elementAt(stack.size() - 1);

      if (delegate.hasUnvisitedNeighbour(node)) {
        Edge edge = delegate.getUnvisitedNeighbour(node);
        delegate.traverse(edge);
        stack.addElement(edge.getTail());
      } else
        stack.removeElementAt(stack.size() - 1);
    }
  }

}