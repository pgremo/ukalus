package ironfist.level.maze.eller;

import ironfist.level.Region;
import ironfist.level.RegionFactory;
import ironfist.level.maze.MazeRegion;
import ironfist.loop.Level;
import ironfist.math.Vector2D;
import ironfist.util.DisjointSet;
import ironfist.util.MersenneTwister;

import java.util.Random;

public class EllerMazeGenerator implements RegionFactory {

  private Random random;
  private int height;
  private int width;

  public EllerMazeGenerator(Random random, int height, int width) {
    this.random = random;
    this.height = ((height - 1) & (Integer.MAX_VALUE - 1)) + 1;
    this.width = ((width - 1) & (Integer.MAX_VALUE - 1)) + 1;
  }

  public Region create() {
    int[][] result = new int[height][width];

    DisjointSet sets = new DisjointSet(width / 2);

    for (int x = 1; x < height; x += 2) {
      int[] current = result[x];
      int[] next = result[x + 1];

      // horizontal passages
      current[1] = 1;
      for (int right = 1; right < width / 2; right++) {
        current[right * 2] = 1;
        int left = right - 1;
        if (sets.find(left) != sets.find(right) && random.nextBoolean()) {
          sets.union(left, right);
          current[right * 2 - 1] = 1;
        }
      }
      // vertical passages
      for (int y = 1; y < width / 2; y++) {
        if (sets.size(y) == 1) {
          next[y * 2] = 1;
        }
      }

    }

    return new MazeRegion(result);
  }

  public static void main(String[] args) {
    RegionFactory generator = new EllerMazeGenerator(new MersenneTwister(), 20,
      80);

    Region region = generator.create();
    region.setLocation(Vector2D.get(0, 0));
    Level level = new Level(new Object[20][80]);
    region.place(level);

    System.out.println(level);
  }

}
