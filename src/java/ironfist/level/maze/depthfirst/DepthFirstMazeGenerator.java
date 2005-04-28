package ironfist.level.maze.depthfirst;

import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class DepthFirstMazeGenerator {

  private static final Vector2D[] DIRECTIONS = new Vector2D[]{
      Vector2D.get(0, 2),
      Vector2D.get(2, 0),
      Vector2D.get(0, -2),
      Vector2D.get(-2, 0)};
  private Random random;
  private int height;
  private int width;
  private boolean[][] cells;
  private Set<Vector2D> visited = new HashSet<Vector2D>();
  private Set<Vector2D> all = new HashSet<Vector2D>();

  public DepthFirstMazeGenerator(Random random, int height, int width) {
    this.random = random;
    this.height = ((height - 1) & (Integer.MAX_VALUE - 1)) + 1;
    this.width = ((width - 1) & (Integer.MAX_VALUE - 1)) + 1;
  }

  public boolean[][] generate() {
    cells = new boolean[height][width];

    for (int x = 1; x < cells.length - 1; x++) {
      for (int y = 1; y < cells[x].length - 1; y++) {
        if (x % 2 == 1 && y % 2 == 1) {
          cells[x][y] = true;
          all.add(Vector2D.get(x, y));
        }
      }
    }

    Stack<Vector2D> path = new Stack<Vector2D>();
    Vector2D current = Vector2D.get((random.nextInt((height - 1) / 2) * 2) + 1,
      (random.nextInt((width - 1) / 2) * 2) + 1);
    visited.add(current);
    while (current != null) {
      List<Vector2D> neighbors = getNeighbors(current);
      if (!neighbors.isEmpty()) {
        Collections.shuffle(neighbors, random);
        Vector2D neighbor = neighbors.get(0);
        Vector2D wall = current.add(neighbor.subtract(current)
          .normal());
        cells[wall.getX()][wall.getY()] = true;
        path.push(current);
        current = neighbor;
        visited.add(current);
      } else {
        current = path.isEmpty() ? null : path.pop();
      }
    }

    return cells;
  }

  private List<Vector2D> getNeighbors(Vector2D location) {
    List<Vector2D> result = new ArrayList<Vector2D>(DIRECTIONS.length);
    for (Vector2D direction : DIRECTIONS) {
      Vector2D neighbor = location.add(direction);
      if (!visited.contains(neighbor) && all.contains(neighbor)) {
        result.add(neighbor);
      }
    }
    return result;
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
    DepthFirstMazeGenerator generator = new DepthFirstMazeGenerator(
      new MersenneTwister(), 20, 80);

    boolean[][] result = generator.generate();

    generator.toString(result);
  }
}