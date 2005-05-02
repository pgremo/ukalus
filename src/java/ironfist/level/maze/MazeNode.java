package ironfist.level.maze;

import ironfist.graph.Edge;
import ironfist.graph.Node;
import ironfist.math.Vector2D;

import java.util.LinkedList;
import java.util.List;

public class MazeNode implements Node {

  private Vector2D location;
  private List<Edge> edges = new LinkedList<Edge>();

  public MazeNode(Vector2D location) {
    this.location = location;
  }

  public void addEdge(Edge edge) {
    edges.add(edge);
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public Vector2D getLocation() {
    return location;
  }
  
  public String toString(){
    return location.toString();
  }

}
