package ironfist.level.maze;

import ironfist.graph.Edge;
import ironfist.graph.GraphTraversalDelegate;
import ironfist.graph.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MazeTraversalDelegate implements GraphTraversalDelegate {

  private Random random;
  private Set<Node> visited = new HashSet<Node>();
  private Set<MazeEdge> path;

  public MazeTraversalDelegate(Node start, Set<MazeEdge> path, Random random) {
    visited.add(start);
    this.path = path;
    this.random = random;
  }

  public Edge getNode(Node node) {
    List<Edge> unvisited = new ArrayList<Edge>();
    for (Edge edge : node.getEdges()) {
      if (!visited.contains(edge.getNode(node))) {
        unvisited.add(edge);
      }
    }
    return unvisited.isEmpty()
        ? null
        : unvisited.get(random.nextInt(unvisited.size()));
  }

  public void traverse(Node node, Edge edge) {
    path.add((MazeEdge) edge);
    visited.add(edge.getNode(node));
  }

}
