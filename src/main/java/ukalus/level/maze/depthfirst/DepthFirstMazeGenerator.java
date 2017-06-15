package ukalus.level.maze.depthfirst;

import ukalus.graph.Node;
import ukalus.graph.NodeStack;
import ukalus.graph.NodeTraversal;
import ukalus.level.Level;
import ukalus.level.Region;
import ukalus.level.RegionFactory;
import ukalus.level.maze.MazeEdge;
import ukalus.level.maze.MazeNode;
import ukalus.level.maze.MazeRegion;
import ukalus.level.maze.MazeTraversalDelegate;
import ukalus.math.Vector2D;
import ukalus.util.MersenneTwister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class DepthFirstMazeGenerator implements RegionFactory {

  private Random random;
  private int height;
  private int width;
  private Map<Vector2D, MazeNode> nodes = new HashMap<Vector2D, MazeNode>();
  private Map<Vector2D, MazeEdge> edges = new HashMap<Vector2D, MazeEdge>();

  public DepthFirstMazeGenerator(Random random, int height, int width) {
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
        } else if (x % 2 == 1 && y % 2 == 1) {
          // node
          getNode(Vector2D.get(x, y));
          cells[x][y] = 1;
        }
      }
    }

    Set<MazeEdge> path = new HashSet<MazeEdge>();

    Node start = new ArrayList<Node>(nodes.values()).get(random.nextInt(nodes.size()));

    NodeTraversal traversal = new NodeTraversal(new MazeTraversalDelegate(start, path, random),
      new NodeStack());

    traversal.traverse(start);

    for (MazeEdge edge : path) {
      cells[edge.getLocation()
        .getX()][edge.getLocation()
        .getY()] = 1;
    }

    return new MazeRegion(cells);
  }

  public static void main(String[] args) {
    RegionFactory generator = new DepthFirstMazeGenerator(
      new MersenneTwister(), 20, 80);

    Region region = generator.create();
    region.setLocation(Vector2D.get(0, 0));
    Level level = new Level(new Object[20][80]);
    region.place(level);

    System.out.println(level);
  }

}
