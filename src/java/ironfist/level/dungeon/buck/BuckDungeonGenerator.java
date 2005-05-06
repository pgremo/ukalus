package ironfist.level.dungeon.buck;

import ironfist.level.maze.MazeGenerator;
import ironfist.level.maze.prim.PrimMazeGenerator;
import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BuckDungeonGenerator {

  private static final Vector2D[] DIRECTIONS = new Vector2D[]{
      Vector2D.get(1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(0, -1),
      Vector2D.get(-1, 0)};

  private Random random = new MersenneTwister();
  private MazeGenerator generator = new PrimMazeGenerator(random, 20, 80);

  public int[][] generate() {
    int[][] result = generator.generate();
    sparcify(result);
    addRooms(result);
//    removeDeadEnds(result);
    return result;
  }

  private void addRooms(int[][] cells) {
    for (int rooms = 0; rooms < 10; rooms++) {
      Room room = createRoom();
      int height = room.getHeight();
      int width = room.getWidth();
      int best = Integer.MAX_VALUE;
      Vector2D bestCell = null;
      for (int x = 1; x < cells.length - height - 1; x++) {
        for (int y = 1; y < cells[x].length - width - 1; y++) {
          Vector2D levelCell = Vector2D.get(x, y);
          room.setLocation(levelCell);
          int cost = room.cost(cells);
          if (cost > 0 && cost < best) {
            best = cost;
            bestCell = levelCell;
          }
        }
      }
      if (best < Integer.MAX_VALUE) {
        room.setLocation(bestCell);
        room.place(cells);
      }
    }
  }

  private Room createRoom() {
    return new Room(random, random.nextInt(4) + 5, random.nextInt(5) + 9);
  }

  private void sparcify(int[][] cells) {
    for (int i = 0; i < 2; i++) {
      for (int x = 1; x < cells.length - 1; x++) {
        for (int y = 1; y < cells[x].length - 1; y++) {
          int count = 0;
          for (Vector2D direction : DIRECTIONS) {
            Vector2D location = Vector2D.get(x, y)
              .add(direction);
            if (cells[location.getX()][location.getY()] > 0) {
              count++;
            }
          }
          if (count == 1) {
            cells[x][y] = 0;
          }
        }
      }
    }
  }

  private void removeDeadEnds(int[][] cells) {
    List<Vector2D> deadEnds = new LinkedList<Vector2D>();
    for (int x = 1; x < cells.length - 1; x++) {
      for (int y = 1; y < cells[x].length - 1; y++) {
        int count = 0;
        Vector2D target = Vector2D.get(x, y);
        for (Vector2D direction : DIRECTIONS) {
          Vector2D location = target.add(direction);
          if (cells[location.getX()][location.getY()] > 0) {
            count++;
          }
        }
        if (count == 1) {
          deadEnds.add(target);
        }
      }
    }
    for (Vector2D cell : deadEnds) {
      int count = 0;
      Vector2D current = cell;
      do {
        Vector2D next = null;
        count = 0;
        for (Vector2D direction : DIRECTIONS) {
          Vector2D location = current.add(direction);
          if (cells[location.getX()][location.getY()] > 0) {
            next = location;
            count++;
          }
        }
        if (count == 1) {
          cells[current.getX()][current.getY()] = 0;
          current = next;
        }
      } while (count == 1);
    }
  }

  public void toString(int[][] cells) {
    StringBuffer result = new StringBuffer();
    for (int x = 0; x < cells.length; x++) {
      for (int y = 0; y < cells[x].length; y++) {
        if (cells[x][y] > 0) {
          result.append(".");
        } else {
          result.append("#");
        }
      }
      result.append("\n");
    }
    System.out.print(result);
  }

  public static void main(String[] args) {
    BuckDungeonGenerator generator = new BuckDungeonGenerator();
    int[][] result = generator.generate();

    generator.toString(result);

  }

}
