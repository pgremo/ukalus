package ironfist.graph;

public interface GraphTraversalDelegate {

  public boolean hasUnvisitedNeighbour(Node node);

  public Edge getUnvisitedNeighbour(Node node);

  public void traverse(Edge edge);

}