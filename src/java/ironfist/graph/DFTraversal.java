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
      Edge edge = delegate.getUnvisitedNeighbour(stack.peek());
      if (edge != null) {
        delegate.traverse(edge);
        stack.push(edge.getTail());
      } else {
        stack.pop();
      }
    }
  }

}