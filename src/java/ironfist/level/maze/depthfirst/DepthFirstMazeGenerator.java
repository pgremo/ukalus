package ironfist.level.maze.depthfirst;

import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DepthFirstMazeGenerator {

  private static final int EVEN_MASK = Integer.MAX_VALUE - 1;
  private static final List<Vector2D> DIRECTIONS = Arrays.asList(new Vector2D[]{
      Vector2D.get(0, 1),
      Vector2D.get(1, 0),
      Vector2D.get(0, -1),
      Vector2D.get(-1, 0)});
  private Random random;
  private int height;
  private int width;
  private boolean[][] walls;

  public boolean[][] generate() {
    walls = new boolean[height][width];

    iterate(Vector2D.get((random.nextInt((height - 1) / 2) * 2) + 1,
      (random.nextInt((width - 1) / 2) * 2) + 1));

    return walls;
  }

  private void iterate(Vector2D location) {
    set(location, true);
    Collections.shuffle(DIRECTIONS, random);
    for (Vector2D direction : DIRECTIONS) {
      Vector2D step1 = location.add(direction);
      Vector2D step2 = step1.add(direction);
      if (contains(step2) && !get(step2)) {
        set(step1, true);
        iterate(step2);
      }
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