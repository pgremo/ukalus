package ironfist.level.maze.kruskal;

import ironfist.level.Level;
import ironfist.level.Region;
import ironfist.level.RegionFactory;
import ironfist.level.maze.MazeRegion;
import ironfist.math.Vector2D;
import ironfist.util.DisjointSet;
import ironfist.util.Loop;
import ironfist.util.MersenneTwister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KruskalMazeGenerator implements RegionFactory {

  private Random random;
  private int height;
  private int width;

  public KruskalMazeGenerator(Random random, int height, int width) {
    this.random = random;
    this.height = ((height - 1) & (Integer.MAX_VALUE - 1)) + 1;
    this.width = ((width - 1) & (Integer.MAX_VALUE - 1)) + 1;
  }

  public Region create() {
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

    return new MazeRegion(nodes);
  }

  public static void main(String[] args) {
    RegionFactory generator = new KruskalMazeGenerator(new MersenneTwister(),
      20, 80);

    Region region = generator.create();
    region.setLocation(Vector2D.get(0, 0));
    Level level = new Level(new Object[20][80]);
    region.place(level);

    System.out.println(level);
  }

}