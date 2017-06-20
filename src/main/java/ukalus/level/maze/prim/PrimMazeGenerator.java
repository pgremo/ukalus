package ukalus.level.maze.prim;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;
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

import java.util.*;

public class PrimMazeGenerator implements RegionFactory<Integer> {

  private Random random;
  private int height;
  private int width;
  private Map<Vector2D, MazeNode> nodes = new HashMap<>();
  private Map<Vector2D, MazeEdge> edges = new HashMap<>();

  public PrimMazeGenerator(Random random, int height, int width) {
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

  public Region<Integer> create() {
    int[][] cells = new int[height][width];

    // create nodes / edges
    for (int x = 1; x < cells.length - 1; x++) {
      for (int y = 1; y < cells[x].length - 1; y++) {
        if (x % 2 == 0 && y % 2 == 1) {
          // vertical edge
          MazeNode head = getNode(Vector2D.Companion.get(x - 1, y));
          MazeNode tail = getNode(Vector2D.Companion.get(x + 1, y));
          MazeEdge edge = getEdge(Vector2D.Companion.get(x, y), head, tail);
          head.addEdge(edge);
          tail.addEdge(edge);
        } else if (x % 2 == 1 && y % 2 == 0) {
          // horizontal edge
          MazeNode head = getNode(Vector2D.Companion.get(x, y - 1));
          MazeNode tail = getNode(Vector2D.Companion.get(x, y + 1));
          MazeEdge edge = getEdge(Vector2D.Companion.get(x, y), head, tail);
          head.addEdge(edge);
          tail.addEdge(edge);
        } else if (x % 2 == 1 && y % 2 == 1) {
          // node
          getNode(Vector2D.Companion.get(x, y));
          cells[x][y] = 1;
        }
      }
    }

    Set<MazeEdge> path = new HashSet<MazeEdge>();

    Node start = new ArrayList<Node>(nodes.values()).get(random.nextInt(nodes.size()));

    NodeTraversal traversal = new NodeTraversal(new MazeTraversalDelegate(start, path, random), new NodeRandom(random));

    traversal.traverse(start);

    for (MazeEdge edge : path) {
      cells[edge.getLocation().getX()][edge.getLocation().getY()] = 1;
    }

    return new MazeRegion(cells);
  }

  public static void main(String[] args) {
    PrimMazeGenerator generator = new PrimMazeGenerator(new RandomAdaptor(new MersenneTwister()), 20, 80);

    Level<Integer> level = new Level<>(new Integer[20][80]);
    Region<Integer> region = generator.create();
    region.place(Vector2D.Companion.get(0, 0), level);

    System.out.println(level);
  }

}
