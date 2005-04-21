package ironfist.generator;

import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.util.Random;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class MazeGenerator {

  private static final int MASK = Integer.MAX_VALUE - 1;
  private static final Vector2D[] DIRECTIONS = new Vector2D[]{
      Vector2D.get(0, 1),
      Vector2D.get(1, 0),
      Vector2D.get(0, -1),
      Vector2D.get(-1, 0)};
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

    iterate(Vector2D.get((random.nextInt((height - 1) / 2) * 2) + 1,
      (random.nextInt((width - 1) / 2) * 2) + 1));

    return walls;
  }

  private void iterate(Vector2D location) {
    set(location, false);
    int direction = random.nextInt(DIRECTIONS.length);
    for (int index = 0; index < DIRECTIONS.length; index++) {
      Vector2D step1 = location.add(DIRECTIONS[direction]);
      if (contains(step1) && get(step1)) {
        Vector2D step2 = step1.add(DIRECTIONS[direction]);
        if (contains(step2) && get(step2)) {
          set(step1, false);
          iterate(step2);
        }
      }
      direction = (direction + 1) % DIRECTIONS.length;
    }
  }

  private void set(Vector2D location, boolean value) {
    walls[location.getX()][location.getY()] = value;

  }

  private boolean get(Vector2D location) {
    return walls[location.getX()][location.getY()];
  }

  private boolean contains(Vector2D location) {
    return (location.getX() > 0) && (location.getX() < height - 1)
        && (location.getY() > 0) && (location.getY() < width - 1);
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
    this.height = ((height - 1) & MASK) + 1;
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
    this.width = ((width - 1) & MASK) + 1;
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
          System.out.print(".");
        }
      }

      System.out.println();
    }

  }
}