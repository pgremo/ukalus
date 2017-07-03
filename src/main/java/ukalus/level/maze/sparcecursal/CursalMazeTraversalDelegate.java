package ukalus.level.maze.sparcecursal;

import ukalus.graph.Edge;
import ukalus.graph.GraphTraversalDelegate;
import ukalus.graph.Node;
import ukalus.level.maze.MazeEdge;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CursalMazeTraversalDelegate implements GraphTraversalDelegate {

  private Random random;
  private Set<MazeEdge> path;

  public CursalMazeTraversalDelegate(Set<MazeEdge> path, Random random) {
    this.path = path;
    this.random = random;
  }

  public Edge getEdge(Node node) {
    Edge result = null;
    List<Edge> edges = new LinkedList<Edge>(node.getEdges());
    edges.retainAll(path);
    if (edges.size() == 1) {
      List<Edge> unvisited = new LinkedList<Edge>(node.getEdges());
      unvisited.removeAll(edges);
      result = unvisited
        .get(random.nextInt(unvisited
          .size()));
    }
    return result;
  }

  public void traverse(Node node, Edge edge) {
    path.add((MazeEdge) edge);
  }

}
