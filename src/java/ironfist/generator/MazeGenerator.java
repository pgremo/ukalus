package ironfist.generator;

import ironfist.geometry.Vector;

import java.util.Arrays;
import java.util.Random;

import random.MersenneTwister;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class MazeGenerator {
  private static final Vector[] DIRECTIONS = new Vector[] {
      new Vector(0, 1), new Vector(1, 0), new Vector(0, -1), new Vector(-1, 0)
    };
  private Random random;
  private int height;
  private int width;
  private boolean[][] walls;

  public boolean[][] generate() {
    walls = new boolean[height][width];

    for (int x = 0; x < walls.length; x++) {
      for (int y = 0; y < walls[x].length; y++) {
        walls[x][y] = true;
      }
    }

    iterate(
      new Vector((random.nextInt((height - 1) / 2) * 2) + 1,
        (random.nextInt((width - 1) / 2) * 2) + 1));

    return walls;
  }

  private void iterate(Vector location) {
    walls[(int) location.getX()][(int) location.getY()] = false;

    int direction = random.nextInt(DIRECTIONS.length);

    for (int index = 0; index < DIRECTIONS.length; index++) {
      Vector nextWall = location.add(DIRECTIONS[direction]);

      if (walls[(int) nextWall.getX()][(int) nextWall.getY()]) {
        Vector nextPassage = nextWall.add(DIRECTIONS[direction]);

        if ((nextPassage.getX() > 0) && (nextPassage.getX() < height) &&
            (nextPassage.getY() > 0) && (nextPassage.getY() < width) &&
            walls[(int) nextPassage.getX()][(int) nextPassage.getY()]) {
          walls[(int) nextWall.getX()][(int) nextWall.getY()] = false;
          iterate(nextPassage);
        }
      }

      direction = (direction + 1) % DIRECTIONS.length;
    }
  }

  /**
   * Returns the random.
   *
   * @return Random
   */
  public Random getRandom() {
    return random;
  }

  /**
   * Sets the random.
   *
   * @param random The random to set
   */
  public void setRandom(Random random) {
    this.random = random;
  }

  /**
   * Returns the height.
   *
   * @return int
   */
  public int getHeight() {
    return height;
  }

  /**
   * Sets the height.
   *
   * @param height The height to set
   */
  public void setHeight(int height) {
    this.height = ((height - 1) / 2 * 2) + 1;
  }

  /**
   * Returns the width.
   *
   * @return int
   */
  public int getWidth() {
    return width;
  }

  /**
   * Sets the width.
   *
   * @param width The width to set
   */
  public void setWidth(int width) {
    this.width = ((width - 1) / 2 * 2) + 1;
  }

  public static void main(String[] args) {
  	long seed = 1;
  	Random random = new MersenneTwister(seed);
    MazeGenerator generator = new MazeGenerator();
    generator.setRandom(random);
    generator.setHeight(13);
    generator.setWidth(13);
    generator.setRandom(new MersenneTwister());

    boolean[][] result = generator.generate();

    for (int x = 0; x < result.length; x++) {
      for (int y = 0; y < result[x].length; y++) {
        if (result[x][y]) {
          System.out.print("#");
        } else {
          System.out.print(" ");
        }
      }

      System.out.println();
    }
    
    generator.setRandom(new MersenneTwister(seed));
    boolean[][] result2 = generator.generate();
    System.out.println("result == result2 = " + Arrays.equals(result, result2));
  }
}
