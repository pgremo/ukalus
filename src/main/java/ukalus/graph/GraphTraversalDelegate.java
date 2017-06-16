package ukalus.graph;

public interface GraphTraversalDelegate {

  Edge getNode(Node node);

  void traverse(Node node, Edge edge);

}