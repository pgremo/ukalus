package ukalus.graph;

public class NodeTraversal {

  private GraphTraversalDelegate delegate;
  private NodeCollection visited;

  public NodeTraversal(GraphTraversalDelegate delegate, NodeCollection visited) {
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