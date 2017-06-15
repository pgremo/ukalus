package ukalus.graph;

public interface GraphTraversalDelegate {

  public Edge getNode(Node node);

  public void traverse(Node node, Edge edge);

}