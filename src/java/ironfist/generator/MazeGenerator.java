package ironfist.generator;

import ironfist.math.Vector;
import ironfist.util.MersenneTwister;

import java.util.Random;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class MazeGenerator {

  private static final Vector[] DIRECTIONS = new Vector[]{
      new Vector(0, 1),
      new Vector(1, 0),
      new Vector(0, -1),
      new Vector(-1, 0)};
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

    iterate(new Vector((random.nextInt((height - 1) / 2) * 2) + 1,
      (random.nextInt((width - 1) / 2) * 2) + 1));

    return walls;
  }

  private void iterate(Vector location) {
    set(location, false);
    int direction = random.nextInt(DIRECTIONS.length);
    for (int index = 0; index < DIRECTIONS.length; index++) {
      Vector step1 = location.add(DIRECTIONS[direction]);
      if (contains(step1) && get(step1)) {
        Vector step2 = step1.add(DIRECTIONS[direction]);
        if (contains(step2) && get(step2)) {
          set(step1, false);
          iterate(step2);
        }
      }
      direction = (direction + 1) % DIRECTIONS.length;
    }
  }

  private void set(Vector location, boolean value) {
    walls[(int) location.getX()][(int) location.getY()] = value;

  }

  private boolean get(Vector nextPassage) {
    return walls[(int) nextPassage.getX()][(int) nextPassage.getY()];
  }

  private boolean contains(Vector nextPassage) {
    return (nextPassage.getX() > 0) && (nextPassage.getX() < height - 1)
        && (nextPassage.getY() > 0) && (nextPassage.getY() < width - 1);
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
   * @param random
   *          The random to set
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
   * @param height
   *          The height to set
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
   * @param width
   *          The width to set
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

  }
}