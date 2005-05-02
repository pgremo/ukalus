package ironfist.graph;

import java.util.Stack;

public class DFTraversal extends GraphTraversal {

  public DFTraversal(Graph graph, GraphTraversalDelegate delegate) {
    super(graph, delegate);
  }

  public void start(Node root) {
    Stack<Node> stack = new Stack<Node>();
    stack.push(root);

    while (!stack.isEmpty()) {
      Node node = stack.peek();
      if (delegate.hasUnvisitedNeighbour(node)) {
        Edge edge = delegate.getUnvisitedNeighbour(node);
        delegate.traverse(edge);
        stack.push(edge.getTail());
      } else {
        stack.pop();
      }
    }
  }

}