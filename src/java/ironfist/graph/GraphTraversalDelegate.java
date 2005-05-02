package ironfist.graph;

public interface GraphTraversalDelegate {

  public Edge getUnvisitedNeighbour(Node node);

  public void traverse(Edge edge);

}