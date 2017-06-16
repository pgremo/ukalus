package ukalus.level.maze.sparcecursal;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;
import ukalus.graph.Edge;
import ukalus.graph.Node;
import ukalus.graph.NodeRandom;
import ukalus.graph.NodeTraversal;
import ukalus.level.Level;
import ukalus.level.Region;
import ukalus.level.RegionFactory;
import ukalus.level.maze.MazeEdge;
import ukalus.level.maze.MazeNode;
import ukalus.level.maze.MazeRegion;
import ukalus.level.maze.MazeTraversalDelegate;
import ukalus.math.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class SparseCursalMazeGenerator implements RegionFactory {

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
    return nodes.computeIfAbsent(location, MazeNode::new);
  }

  private MazeEdge getEdge(Vector2D location, MazeNode head, MazeNode tail) {
    return edges.computeIfAbsent(location, l -> new MazeEdge(l, head, tail));
  }

  public Region create() {
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

    new NodeTraversal(new MazeTraversalDelegate(start, path, random),
      new NodeRandom(random)).traverse(start);

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

    return new MazeRegion(cells);
  }

  private void closeDeadEnds(Set<MazeEdge> path) {
    for (MazeNode node : nodes.values()) {
      List<Edge> visited = new LinkedList<Edge>(node.getEdges());
      visited.retainAll(path);
      if (visited.size() == 1) {
        new NodeTraversal(new CursalMazeTraversalDelegate(path, random),
          new VisitedLast()).traverse(node);
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

  public static void main(String[] args) {
    RegionFactory generator = new SparseCursalMazeGenerator(
      new RandomAdaptor(new MersenneTwister()), 20, 80);

    Level level = new Level(new Object[20][80]);
    Region region = generator.create();
    region.place(Vector2D.get(0, 0), level);

    System.out.println(level);
  }

}