package ironfist.level.maze.kurskal;

import ironfist.util.DisjointSet;
import ironfist.util.Loop;
import ironfist.util.MersenneTwister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KurskalMazeGenerator {

  private Random random;
  private int height;
  private int width;

  public KurskalMazeGenerator(Random random, int height, int width) {
    this.random = random;
    this.height = ((height - 1) & (Integer.MAX_VALUE - 1)) + 1;
    this.width = ((width - 1) & (Integer.MAX_VALUE - 1)) + 1;
  }

  public boolean[][] generate() {
    boolean[][] result = new boolean[height][width];
    List<WallCell> walls = new ArrayList<WallCell>(height * width);

    // add walls / open cells
    for (int x = 1; x < result.length - 1; x++) {
      for (int y = 1; y < result[x].length - 1; y++) {
        if (x % 2 == 0 && y % 2 == 1) {
          // horizontal wall
          walls.add(new WallCell(x, y, (x - 1) * width + y, (x + 1) * width + y));
        } else if (x % 2 == 1 && y % 2 == 0) {
          // vertical wall
          walls.add(new WallCell(x, y, x * width + y - 1, x * width + y + 1));
        } else if (x % 2 == 1 && y % 2 == 1) {
          // open cell
          result[x][y] = true;
        }
      }
    }

    Collections.shuffle(walls, random);
    new Loop<WallCell>(walls).forEach(new RemoveWall(new DisjointSet(height
        * width), result));

    return result;
  }

  public void toString(boolean[][] result) {
    for (int x = 0; x < result.length; x++) {
      for (int y = 0; y < result[x].length; y++) {
        if (result[x][y]) {
          System.out.print(".");
        } else {
          System.out.print("#");
        }
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    KurskalMazeGenerator generator = new KurskalMazeGenerator(
      new MersenneTwister(), 20, 80);
    boolean[][] result = generator.generate();

    generator.toString(result);

  }

}