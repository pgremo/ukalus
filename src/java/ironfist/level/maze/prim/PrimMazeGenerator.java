package ironfist.level.maze.prim;

import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrimMazeGenerator {

  private static final Vector2D[] DIRECTIONS = new Vector2D[]{
      Vector2D.get(0, 2),
      Vector2D.get(2, 0),
      Vector2D.get(0, -2),
      Vector2D.get(-2, 0)};
  private Random random;
  private int height;
  private int width;
  private boolean[][] cells;

  public PrimMazeGenerator(Random random, int height, int width) {
    this.random = random;
    this.height = ((height - 1) & (Integer.MAX_VALUE - 1)) + 1;
    this.width = ((width - 1) & (Integer.MAX_VALUE - 1)) + 1;
  }

  public boolean[][] generate() {
    Set<Vector2D> in = new HashSet<Vector2D>();
    Set<Vector2D> frontier = new HashSet<Vector2D>();
    Set<Vector2D> out = new HashSet<Vector2D>();
    cells = new boolean[height][width];

    for (int x = 1; x < cells.length - 1; x++) {
      for (int y = 1; y < cells[x].length - 1; y++) {
        if (x % 2 == 1 && y % 2 == 1) {
          cells[x][y] = true;
          out.add(Vector2D.get(x, y));
        }
      }
    }

    Vector2D current = Vector2D.get((random.nextInt((height - 1) / 2) * 2) + 1,
      (random.nextInt((width - 1) / 2) * 2) + 1);
    in.add(current);
    frontier.addAll(getNeighbors(current));
    while (!frontier.isEmpty()) {
      Iterator<Vector2D> iterator = frontier.iterator();
      current = iterator.next();
      iterator.remove();
      in.add(current);
      List<Vector2D> targets = new LinkedList<Vector2D>();
      for (Vector2D neighbor : getNeighbors(current)) {
        if (out.remove(neighbor)) {
          frontier.add(neighbor);
        }
        if (in.remove(neighbor)) {
          targets.add(neighbor);
        }
      }
      Vector2D target = targets.get(random.nextInt(targets.size()));
      Vector2D wall = current.add(target.subtract(current)
        .normal());
      cells[wall.getX()][wall.getY()] = true;
      toString(cells);
      System.out.println();
    }

    return cells;
  }

  private List<Vector2D> getNeighbors(Vector2D location) {
    List<Vector2D> result = new ArrayList<Vector2D>(DIRECTIONS.length);
    for (Vector2D direction : DIRECTIONS) {
      Vector2D neighbor = location.add(direction);
      if (neighbor.getX() >= 0 && neighbor.getX() < height
          && neighbor.getY() >= 0 && neighbor.getY() < width) {
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
    PrimMazeGenerator generator = new PrimMazeGenerator(new MersenneTwister(),
      10, 10);

    boolean[][] result = generator.generate();

    generator.toString(result);
  }

}
