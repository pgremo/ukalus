package ironfist.level.maze.sparcecursal;

import ironfist.graph.Edge;
import ironfist.graph.Node;
import ironfist.graph.NodeTraversal;
import ironfist.graph.NodeRandom;
import ironfist.level.maze.MazeEdge;
import ironfist.level.maze.MazeGenerator;
import ironfist.level.maze.MazeNode;
import ironfist.level.maze.MazeTraversalDelegate;
import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class SparseCursalMazeGenerator implements MazeGenerator {

  private Random random;
  private int height;
  private int width;
  private Map<Vector2D, MazeNode> nodes = new HashMap<Vector2D, MazeNode>();
  private Map<Vector2D, MazeEdge> edges = new HashMap<Vector2D, MazeEdge>();

  public SparseCursalMazeGenerator(Random random, int height, int width) {
    this.random = random;
    this.height = ((height - 1) & (Integer.MAX_VALUE - 1)) + 1;
    this.width = ((width - 1) & (Integer.MAX_VALUE - 1)) + 1;
  }

  private MazeNode getNode(Vector2D location) {
    MazeNode result = nodes.get(location);
    if (result == null) {
      result = new MazeNode(location);
      nodes.put(location, result);
    }
    return result;
  }

  private MazeEdge getEdge(Vector2D location, MazeNode head, MazeNode tail) {
    MazeEdge result = edges.get(location);
    if (result == null) {
      result = new MazeEdge(location, head, tail);
      edges.put(location, result);
    }
    return result;
  }

  public int[][] generate() {
    int[][] cells = new int[height][width];

    // create nodes / edges
    for (int x = 1; x < cells.length - 1; x++) {
      for (int y = 1; y < cells[x].length - 1; y++) {
        if (x % 2 == 0 && y % 2 == 1) {
          // vertical edge
          MazeNode head = getNode(Vector2D.get(x - 1, y));
          MazeNode tail = getNode(Vector2D.get(x + 1, y));
          MazeEdge edge = getEdge(Vector2D.get(x, y), head, tail);
          head.addEdge(edge);
          tail.addEdge(edge);
        } else if (x % 2 == 1 && y % 2 == 0) {
          // horizontal edge
          MazeNode head = getNode(Vector2D.get(x, y - 1));
          MazeNode tail = getNode(Vector2D.get(x, y + 1));
          MazeEdge edge = getEdge(Vector2D.get(x, y), head, tail);
          head.addEdge(edge);
          tail.addEdge(edge);
        }
      }
    }

    Set<MazeEdge> path = new HashSet<MazeEdge>();

    Node start = new ArrayList<Node>(nodes.values()).get(random.nextInt(nodes.size()));

    new NodeTraversal(new MazeTraversalDelegate(start, path, random), new NodeRandom(random)).traverse(start);

    sparcify(path, 4);

    closeDeadEnds(path);

    for (MazeEdge edge : path) {
      Vector2D location = edge.getLocation();
      cells[location.getX()][location.getY()] = 1;
      for (Node node : edge.getNodes()) {
        location = ((MazeNode) node).getLocation();
        cells[location.getX()][location.getY()] = 1;
      }
    }

    return cells;
  }

  private void closeDeadEnds(Set<MazeEdge> path) {
    for (MazeNode node : nodes.values()) {
      List<Edge> visited = new LinkedList<Edge>(node.getEdges());
      visited.retainAll(path);
      if (visited.size() == 1) {
        new NodeTraversal(new CursalMazeTraversalDelegate(path, random), new VisitedLast()).traverse(node);
      }
    }
  }

  private void sparcify(Set<MazeEdge> path, int maxSteps) {
    for (int i = 0; i < maxSteps; i++) {
      for (MazeNode node : nodes.values()) {
        List<Edge> visited = new LinkedList<Edge>(node.getEdges());
        visited.retainAll(path);
        if (visited.size() == 1) {
          path.remove(visited.get(0));
        }
      }
    }
  }

  public void toString(int[][] cells) {
    StringBuffer result = new StringBuffer();
    for (int x = 0; x < cells.length; x++) {
      for (int y = 0; y < cells[x].length; y++) {
        if (cells[x][y] == 1) {
          result.append(".");
        } else {
          result.append("#");
        }
      }
      result.append("\n");
    }
    System.out.print(result);
  }

  public static void main(String[] args) {
    SparseCursalMazeGenerator generator = new SparseCursalMazeGenerator(
      new MersenneTwister(), 20, 80);

    int[][] result = generator.generate();

    generator.toString(result);
  }

}
