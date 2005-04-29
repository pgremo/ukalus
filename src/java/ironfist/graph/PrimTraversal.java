package ironfist.graph;

import java.util.Vector;

public class PrimTraversal extends GraphTraversal {

  public PrimTraversal(Graph graph, GraphTraversalDelegate delegate) {
    super(graph, delegate);
  }

  public void start(Node root) {
    Vector<Node> shell = new Vector<Node>();
    shell.addElement(root);

    for (int i; !shell.isEmpty();) {
      Node node = shell.elementAt(i = (int) (shell.size() * Math.random()));

      if (delegate.hasUnvisitedNeighbour(node)) {
        Edge edge = delegate.getUnvisitedNeighbour(node);
        delegate.traverse(edge);
        shell.addElement(edge.getTail());
      } else
        shell.removeElementAt(i);
    }
  }

}