package ironfist.level.dungeon.buck;

import ironfist.math.Vector2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Room {

  private static final Vector2D[] DIRECTIONS = new Vector2D[]{
      Vector2D.get(1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(0, -1),
      Vector2D.get(-1, 0)};

  private Random random;
  private Vector2D location;
  private int height;
  private int width;

  public Room(Random random, int height, int width) {
    this.random = random;
    this.height = height;
    this.width = width;
  }

  public void place(int[][] level) {
    int startX = location.getX();
    int startY = location.getY();

    // interior
    for (int x = startX + 1; x < startX + height - 1; x++) {
      for (int y = startY + 1; y < startY + width - 1; y++) {
        level[x][y] = 2;
      }
    }

    // corners
    level[startX][startY] = 0;
    level[startX + height][startY] = 0;
    level[startX][startY + width] = 0;
    level[startX + height][startY + width] = 0;

    // walls and doors
    List<List<Vector2D>> all = new LinkedList<List<Vector2D>>();

    // left
    List<Vector2D> open = new LinkedList<Vector2D>();
    all.add(open);
    for (int i = startX + 1; i < startX + height; i++) {
      if (level[i][startY - 1] == 0) {
        if (!open.isEmpty()) {
          open = new LinkedList<Vector2D>();
          all.add(open);
        }
      } else {
        open.add(Vector2D.get(i, startY));
      }
      level[i][startY] = 0;
    }

    // right
    open = new LinkedList<Vector2D>();
    all.add(open);
    for (int i = startX + 1; i < startX + height; i++) {
      if (level[i][startY + width] == 0) {
        if (!open.isEmpty()) {
          open = new LinkedList<Vector2D>();
          all.add(open);
        }
      } else {
        open.add(Vector2D.get(i, startY + width - 1));
      }
      level[i][startY + width - 1] = 0;
    }

    // top
    open = new LinkedList<Vector2D>();
    all.add(open);
    for (int i = startY + 1; i < startY + width; i++) {
      if (level[startX - 1][i] == 0) {
        if (!open.isEmpty()) {
          open = new LinkedList<Vector2D>();
          all.add(open);
        }
      } else {
        open.add(Vector2D.get(startX, i));
      }
      level[startX][i] = 0;
    }

    // bottom
    open = new LinkedList<Vector2D>();
    all.add(open);
    for (int i = startY + 1; i < startY + width; i++) {
      if (level[startX + height][i] == 0) {
        if (!open.isEmpty()) {
          open = new LinkedList<Vector2D>();
          all.add(open);
        }
      } else {
        open.add(Vector2D.get(startX + height - 1, i));
      }
      level[startX + height - 1][i] = 0;
    }

    // place doors
//    for (List<Vector2D> list : all) {
//      if (!list.isEmpty()) {
//        Vector2D location = list.get(random.nextInt(list.size()));
//        level[location.getX()][location.getY()] = 1;
//      }
//    }
  }

  public int cost(int[][] cells) {
    int result = 0;
    for (int x = -1; x <= height; x++) {
      for (int y = -1; y <= width; y++) {
        Vector2D target = location.add(Vector2D.get(x, y));
        if (cells[target.getX()][target.getY()] == 1) {
          if (x == -1 || x == height || y == -1 || y == height) {
            result++;
          } else {
            result += 3;
          }
        }
        if (cells[target.getX()][target.getY()] > 1) {
          result += 100;
        }
      }
    }
    return result;
  }

  public Vector2D getLocation() {
    return location;
  }

  public void setLocation(Vector2D location) {
    this.location = location;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

}
