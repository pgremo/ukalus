package ironfist.graph;

public class NodeTraversal {

  private Graph graph;
  private GraphTraversalDelegate delegate;
  private VisitedList<Node> visited;

  public NodeTraversal(Graph graph, GraphTraversalDelegate delegate,
      VisitedList<Node> visited) {
    this.graph = graph;
    this.delegate = delegate;
    this.visited = visited;
  }

  public void traverse(Node root) {
    visited.add(root);
    while (!visited.isEmpty()) {
      Node node = visited.get();
      Edge edge = delegate.getNode(node);
      if (edge != null) {
        delegate.traverse(node, edge);
        visited.add(edge.getNode(node));
      } else {
        visited.remove();
      }
    }
  }

}