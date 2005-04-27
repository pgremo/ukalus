package ironfist.level.maze.kurskal;

import ironfist.util.DisjointSet;
import ironfist.util.Loop;
import ironfist.util.MersenneTwister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KurskalMazeGenerator {

  private static final int EVEN_MASK = Integer.MAX_VALUE - 1;
  private Random random;
  private int height;
  private int width;

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

  public void setRandom(Random random) {
    this.random = random;
  }

  public void setHeight(int height) {
    this.height = ((height - 1) & EVEN_MASK) + 1;
  }

  public void setWidth(int width) {
    this.width = ((width - 1) & EVEN_MASK) + 1;
  }

  public static void main(String[] args) {
    KurskalMazeGenerator generator = new KurskalMazeGenerator();
    generator.setRandom(new MersenneTwister(1));
    generator.setHeight(20);
    generator.setWidth(80);
    generator.setRandom(new MersenneTwister());

    boolean[][] result = generator.generate();

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
}