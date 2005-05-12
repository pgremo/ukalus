package ironfist.level.maze.eller;

import ironfist.level.maze.MazeGenerator;
import ironfist.util.DisjointSet;
import ironfist.util.MersenneTwister;

import java.util.Random;

public class EllerMazeGenerator implements MazeGenerator {

  private Random random;
  private int height;
  private int width;

  public EllerMazeGenerator(Random random, int height, int width) {
    this.random = random;
    this.height = ((height - 1) & (Integer.MAX_VALUE - 1)) + 1;
    this.width = ((width - 1) & (Integer.MAX_VALUE - 1)) + 1;
  }

  public int[][] generate() {
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
          current[left * 2 + 1] = 1;
        }
      }
      // vertical passages
      for (int y = 0; y < width / 2; y++) {
        if (sets.size(y) == 1) {
          next[y * 2] = 1;
        }
      }
      toString(result);

    }

    return result;
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
    EllerMazeGenerator generator = new EllerMazeGenerator(
      new MersenneTwister(), 20, 80);
    int[][] result = generator.generate();

    generator.toString(result);

  }

}
