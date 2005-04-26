package ironfist.generator;

import ironfist.util.MersenneTwister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KurskalMazeGenerator {

  private static final int MASK = Integer.MAX_VALUE - 1;
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
          WallCell wall = new WallCell(x, y, (x - 1) * width + y, (x + 1)
              * width + y);
          walls.add(wall);
        } else if (x % 2 == 1 && y % 2 == 0) {
          // vertical wall
          WallCell wall = new WallCell(x, y, x * width + y - 1, x * width + y
              + 1);
          walls.add(wall);
        } else if (x % 2 == 1 && y % 2 == 1) {
          // open cell
          result[x][y] = true;
        }
      }
    }

    DisjointSet connected = new DisjointSet(height * width);
    Collections.shuffle(walls, random);

    for (WallCell wall : walls) {
      if (connected.find(wall.left) != connected.find(wall.right)) {
        connected.union(wall.left, wall.right);
        result[wall.x][wall.y] = true;
      }
    }

    return result;
  }

  private class WallCell {

    int x;
    int y;
    int left;
    int right;

    public WallCell(int x, int y, int left, int right) {
      this.x = x;
      this.y = y;
      this.left = left;
      this.right = right;
    }
  }

  public void setRandom(Random random) {
    this.random = random;
  }

  public void setHeight(int height) {
    this.height = ((height - 1) & MASK) + 1;
  }

  public void setWidth(int width) {
    this.width = ((width - 1) & MASK) + 1;
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