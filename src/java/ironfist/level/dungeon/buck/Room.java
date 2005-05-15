package ironfist.level.dungeon.buck;

import ironfist.math.Vector2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Room {

  private Random random;
  private Vector2D location;
  private int height;
  private int width;
  private int id;

  public Room(Random random, int height, int width, int id) {
    this.random = random;
    this.height = height;
    this.width = width;
    this.id = id;
  }

  public void place(int[][] level) {
    int startX = location.getX();
    int startY = location.getY();

    // interior
    for (int x = startX + 1; x < startX + height - 1; x++) {
      for (int y = startY + 1; y < startY + width - 1; y++) {
        level[x][y] = id;
      }
    }

    // corners
    level[startX][startY] = 0;
    level[startX + height - 1][startY] = 0;
    level[startX][startY + width - 1] = 0;
    level[startX + height - 1][startY + width - 1] = 0;

    List<List<Vector2D>> all = new LinkedList<List<Vector2D>>();

    // west
    List<Vector2D> open = new LinkedList<Vector2D>();
    for (int i = startX + 1; i < startX + height - 1; i++) {
      if (startY > 0) {
        if (level[i][startY - 1] == 0) {
          if (!open.isEmpty()) {
            all.add(open);
            open = new LinkedList<Vector2D>();
          }
        } else {
          open.add(Vector2D.get(i, startY));
        }
      }
      level[i][startY] = 0;
    }
    if (!open.isEmpty()) {
      all.add(open);
      open = new LinkedList<Vector2D>();
    }

    // east
    for (int i = startX + 1; i < startX + height - 1; i++) {
      if (startY + width < level[startX].length) {
        if (level[i][startY + width] == 0) {
          if (!open.isEmpty()) {
            all.add(open);
            open = new LinkedList<Vector2D>();
          }
        } else {
          open.add(Vector2D.get(i, startY + width - 1));
        }
      }
      level[i][startY + width - 1] = 0;
    }
    if (!open.isEmpty()) {
      all.add(open);
      open = new LinkedList<Vector2D>();
    }

    // north
    for (int i = startY + 1; i < startY + width - 1; i++) {
      if (startX > 0) {
        if (level[startX - 1][i] == 0) {
          if (!open.isEmpty()) {
            all.add(open);
            open = new LinkedList<Vector2D>();
          }
        } else {
          open.add(Vector2D.get(startX, i));
        }
      }
      level[startX][i] = 0;
    }
    if (!open.isEmpty()) {
      all.add(open);
      open = new LinkedList<Vector2D>();
    }

    // south
    for (int i = startY + 1; i < startY + width - 1; i++) {
      if (startX + height < level.length) {
        if (level[startX + height][i] == 0) {
          if (!open.isEmpty()) {
            all.add(open);
            open = new LinkedList<Vector2D>();
          }
        } else {
          open.add(Vector2D.get(startX + height - 1, i));
        }
      }
      level[startX + height - 1][i] = 0;
    }
    if (!open.isEmpty()) {
      all.add(open);
    }

    // pick door ways
    for (List<Vector2D> list : all) {
      if (!list.isEmpty()) {
        Vector2D location = list.get(random.nextInt(list.size()));
        level[location.getX()][location.getY()] = 100;
      }
    }
  }

  public int cost(int[][] cells) {
    int result = 0;
    int startX = location.getX();
    int startY = location.getY();
    if (startY >= 0 && startY + width <= cells[startX].length && startX >= 0
        && startX + height <= cells.length && cells[startX][startY] == 0
        && cells[startX][startY + width - 1] == 0
        && cells[startX + height - 1][startY] == 0
        && cells[startX + height - 1][startY + width - 1] == 0) {

      if (startY > 0 && startY + width < cells[startX].length) {
        for (int x = startX + 1; x < startX + height - 1; x++) {
          if (cells[x][startY - 1] == 1) {
            result++;
          }
          if (cells[x][startY + width] == 1) {
            result++;
          }
        }
      }
      if (startX > 0 && startX + height < cells.length) {
        for (int y = startY + 1; y < startY + width - 1; y++) {
          if (cells[startX - 1][y] == 1) {
            result++;
          }
          if (cells[startX + height][y] == 1) {
            result++;
          }
        }
      }
      for (int x = startX + 1; x < startX + height - 1; x++) {
        for (int y = startY + 1; y < startY + width - 1; y++) {
          if (cells[x][y] == 1) {
            result += 3;
          }
          if (cells[x][y] > 1) {
            result += 100;
          }
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