package ironfist.generator;

import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.util.Random;

public class DepthFirstMazeGenerator {

  private static final int MASK = Integer.MAX_VALUE - 1;
  private static final Vector2D[] DIRECTIONS = new Vector2D[]{
      Vector2D.get(0, 1),
      Vector2D.get(1, 0),
      Vector2D.get(0, -1),
      Vector2D.get(-1, 0)};
  private Random random;
  private int height;
  private int width;
  private boolean[][] result;

  public boolean[][] generate() {
    result = new boolean[height][width];

    iterate(Vector2D.get((random.nextInt((height - 1) / 2) * 2) + 1,
      (random.nextInt((width - 1) / 2) * 2) + 1));

    return result;
  }

  private void iterate(Vector2D location) {
    result[location.getX()][location.getY()] = true;
    int direction = random.nextInt(DIRECTIONS.length);
    for (int index = 0; index < DIRECTIONS.length; index++) {
      Vector2D step1 = location.add(DIRECTIONS[direction]);
      if (((step1.getX() > 0) && (step1.getX() < height - 1)
          && (step1.getY() > 0) && (step1.getY() < width - 1))
          && !result[step1.getX()][step1.getY()]) {
        Vector2D step2 = step1.add(DIRECTIONS[direction]);
        if (((step2.getX() > 0) && (step2.getX() < height - 1)
            && (step2.getY() > 0) && (step2.getY() < width - 1))
            && !result[step2.getX()][step2.getY()]) {
          result[step1.getX()][step1.getY()] = true;
          iterate(step2);
        }
      }
      direction = (direction + 1) % DIRECTIONS.length;
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
    long seed = 1;
    Random random = new MersenneTwister(seed);
    DepthFirstMazeGenerator generator = new DepthFirstMazeGenerator();
    generator.setRandom(random);
    generator.setHeight(13);
    generator.setWidth(13);
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