package ironfist.level.maze.kruskal;

import ironfist.level.maze.MazeGenerator;
import ironfist.util.DisjointSet;
import ironfist.util.Loop;
import ironfist.util.MersenneTwister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KruskalMazeGenerator implements MazeGenerator {

  private Random random;
  private int height;
  private int width;

  public KruskalMazeGenerator(Random random, int height, int width) {
    this.random = random;
    this.height = ((height - 1) & (Integer.MAX_VALUE - 1)) + 1;
    this.width = ((width - 1) & (Integer.MAX_VALUE - 1)) + 1;
  }

  public int[][] generate() {
    int[][] nodes = new int[height][width];
    List<EdgeCell> edges = new ArrayList<EdgeCell>(height * width);

    // add walls / open cells
    for (int x = 1; x < nodes.length - 1; x++) {
      for (int y = 1; y < nodes[x].length - 1; y++) {
        if (x % 2 == 0 && y % 2 == 1) {
          // horizontal wall
          edges.add(new EdgeCell(x, y, (x - 1) * width + y, (x + 1) * width + y));
        } else if (x % 2 == 1 && y % 2 == 0) {
          // vertical wall
          edges.add(new EdgeCell(x, y, x * width + y - 1, x * width + y + 1));
        } else if (x % 2 == 1 && y % 2 == 1) {
          // open cell
          nodes[x][y] = 1;
        }
      }
    }

    Collections.shuffle(edges, random);
    new Loop<EdgeCell>(edges).forEach(new TraverseEdge(new DisjointSet(height
        * width), nodes));

    return nodes;
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
    KruskalMazeGenerator generator = new KruskalMazeGenerator(
      new MersenneTwister(), 20, 80);
    int[][] result = generator.generate();

    generator.toString(result);

  }

}