package ironfist.level.dungeon.buck;

import ironfist.level.maze.MazeGenerator;
import ironfist.level.maze.prim.PrimMazeGenerator;
import ironfist.math.Vector2D;
import ironfist.util.Loop;
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
    removeDeadEnds(result);
    return result;
  }

  private void addRooms(int[][] cells) {
    for (int rooms = 0; rooms < 15; rooms++) {
      List<Vector2D> list = createRoom();
      Dimension dimension = new Dimension();
      new Loop<Vector2D>(list).forEach(dimension);
      int height = dimension.getHeight();
      int width = dimension.getWidth();
      int best = Integer.MAX_VALUE;
      Vector2D bestCell = null;
      for (int x = 1; x < cells.length - height - 1; x++) {
        for (int y = 1; y < cells[x].length - width - 1; y++) {
          int cost = 0;
          Vector2D levelCell = Vector2D.get(x, y);
          for (Vector2D roomCell : list) {
            Vector2D target = levelCell.add(roomCell);
            for (Vector2D direction : DIRECTIONS) {
              Vector2D location = target.add(direction);
              if (cells[location.getX()][location.getY()] == 1) {
                cost += 3;
              }
              if (cells[location.getX()][location.getY()] > 1) {
                cost += 100;
              }
            }
            if (cells[target.getX()][target.getY()] == 1) {
              cost += 3;
            }
            if (cells[target.getX()][target.getY()] > 1) {
              cost += 100;
            }
          }
          if (cost > 0 && cost < best) {
            best = cost;
            bestCell = levelCell;
          }
        }
      }
      if (best < Integer.MAX_VALUE) {
        new Loop<Vector2D>(list).forEach(new Place(cells, bestCell));
      }
    }
  }

  private List<Vector2D> createRoom() {
    int height = random.nextInt(5) + 2;
    int width = random.nextInt(7) + 5;
    List<Vector2D> list = new LinkedList<Vector2D>();
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        list.add(Vector2D.get(x, y));
      }
    }
    return list;
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
      Vector2D step = cell;
      do {
        Vector2D next = null;
        count = 0;
        for (Vector2D direction : DIRECTIONS) {
          Vector2D location = step.add(direction);
          if (cells[location.getX()][location.getY()] > 0) {
            next = location;
            count++;
          }
        }
        if (count == 1) {
          cells[step.getX()][step.getY()] = 0;
          step = next;
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
