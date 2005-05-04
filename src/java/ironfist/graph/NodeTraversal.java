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

  public void start(Node root) {
    visited.add(root);

    while (!visited.isEmpty()) {
      Edge edge = delegate.getUnvisitedNeighbour(visited.get());

      if (edge != null) {
        delegate.traverse(edge);
        visited.add(edge.getTail());
      } else {
        visited.remove();
      }
    }
  }

}