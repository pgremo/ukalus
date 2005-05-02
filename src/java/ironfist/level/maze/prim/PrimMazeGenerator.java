package ironfist.level.maze.prim;

import ironfist.graph.Node;
import ironfist.graph.PrimTraversal;
import ironfist.level.maze.MazeEdge;
import ironfist.level.maze.MazeNode;
import ironfist.level.maze.MazeTraversalDelegate;
import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PrimMazeGenerator {

  private Random random;
  private int height;
  private int width;
  private boolean[][] cells;
  private Map<Vector2D, MazeNode> nodes = new HashMap<Vector2D, MazeNode>();

  public PrimMazeGenerator(Random random, int height, int width) {
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

  public boolean[][] generate() {
    cells = new boolean[height][width];

    // create nodes / edges
    for (int x = 1; x < cells.length - 1; x++) {
      for (int y = 1; y < cells[x].length - 1; y++) {
        if (x % 2 == 0 && y % 2 == 1) {
          // vertical edge
          MazeNode head = getNode(Vector2D.get(x - 1, y));
          MazeNode tail = getNode(Vector2D.get(x + 1, y));
          head.addEdge(new MazeEdge(Vector2D.get(x, y), head, tail));
          tail.addEdge(new MazeEdge(Vector2D.get(x, y), tail, head));
        } else if (x % 2 == 1 && y % 2 == 0) {
          // horizontal edge
          MazeNode head = getNode(Vector2D.get(x, y - 1));
          MazeNode tail = getNode(Vector2D.get(x, y + 1));
          head.addEdge(new MazeEdge(Vector2D.get(x, y), head, tail));
          tail.addEdge(new MazeEdge(Vector2D.get(x, y), tail, head));
        } else if (x % 2 == 1 && y % 2 == 1) {
          // node
          getNode(Vector2D.get(x, y));
          cells[x][y] = true;
        }
      }
    }

    Node start = new ArrayList<Node>(nodes.values()).get(random.nextInt(nodes.size()));
    PrimTraversal traversal = new PrimTraversal(null,
      new MazeTraversalDelegate(cells, random), random);
    traversal.start(start);

    return cells;
  }

  public void toString(boolean[][] result) {
    PrintStream out = System.out;
    for (int x = 0; x < result.length; x++) {
      for (int y = 0; y < result[x].length; y++) {
        if (result[x][y]) {
          out.print(".");
        } else {
          out.print("#");
        }
      }
      out.println();
    }
  }

  public static void main(String[] args) {
    PrimMazeGenerator generator = new PrimMazeGenerator(new MersenneTwister(),
      20, 80);

    boolean[][] result = generator.generate();

    generator.toString(result);
  }

}
