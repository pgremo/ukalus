package ironfist.level.maze;

import ironfist.graph.Edge;
import ironfist.graph.GraphTraversalDelegate;
import ironfist.graph.Node;
import ironfist.math.Vector2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MazeTraversalDelegate implements GraphTraversalDelegate {
  private boolean cells[][];
  private Random random;
  private Set<Node> visited = new HashSet<Node>();

  public MazeTraversalDelegate(boolean[][] cells, Random random) {
    this.cells = cells;
    this.random = random;
  }

  public boolean hasUnvisitedNeighbour(Node node) {
    List<Edge> unvisited = getAllUnvisited(node);
    return !unvisited.isEmpty();
  }

  private List<Edge> getAllUnvisited(Node node) {
    List<Edge> unvisited = new ArrayList<Edge>();
    for (Edge edge : node.getEdges()) {
      if (!visited.contains(edge.getTail())) {
        unvisited.add(edge);
      }
    }
    return unvisited;
  }

  public Edge getUnvisitedNeighbour(Node node) {
    List<Edge> unvisited = getAllUnvisited(node);
    return unvisited.isEmpty() ? null
        : unvisited.get(random.nextInt(unvisited.size()));
  }

  public void traverse(Edge edge) {
    Vector2D location = ((MazeEdge) edge).getLocation();
    cells[location.getX()][location.getY()] = true;
    visited.add(edge.getHead());
    visited.add(edge.getTail());

  }

}
