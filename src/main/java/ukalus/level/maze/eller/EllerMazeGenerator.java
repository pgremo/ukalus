package ukalus.level.maze.eller;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;
import ukalus.level.Level;
import ukalus.level.Region;
import ukalus.level.RegionFactory;
import ukalus.level.maze.MazeRegion;
import ukalus.math.Vector2D;
import ukalus.util.DisjointSet;

import java.util.Random;

public class EllerMazeGenerator implements RegionFactory<Integer> {

  private Random random;
  private int height;
  private int width;

  public EllerMazeGenerator(Random random, int height, int width) {
    this.random = random;
    this.height = ((height - 1) & (Integer.MAX_VALUE - 1)) + 1;
    this.width = ((width - 1) & (Integer.MAX_VALUE - 1)) + 1;
  }

  public Region<Integer> create() {
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
    RegionFactory<Integer> generator = new EllerMazeGenerator(new RandomAdaptor(new MersenneTwister()), 20,
      80);

    Region<Integer> region = generator.create();
    Level<Integer> level = new Level<>(new Integer[20][80]);
    region.place(Vector2D.Companion.get(0, 0), level);

    System.out.println(level);
  }

}
