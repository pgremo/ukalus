package ironfist.level.dungeon.buck;

import ironfist.level.maze.MazeGenerator;
import ironfist.level.maze.prim.PrimMazeGenerator;
import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BuckDungeonGenerator {

  private static final Vector2D[] DIRECTIONS = new Vector2D[] {
      Vector2D.get(1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(0, -1),
      Vector2D.get(-1, 0) };

  private Random random = new MersenneTwister();
  private MazeGenerator generator = new PrimMazeGenerator(random, 20, 80);
  private int sparceness = 7;
  private int maxRooms = 12;
  private int minRoomHeight = 5;
  private int maxRoomHeight = 9;
  private int minRoomWidth = 9;
  private int maxRoomWidth = 14;

  private List<Vector2D> cellsList;

  public int[][] generate() {
    int[][] result = generator.generate();
    cellsList = new LinkedList<Vector2D>();
    for (int x = 0; x < result.length; x++) {
      for (int y = 0; y < result[x].length; y++) {
        cellsList.add(Vector2D.get(x, y));
      }
    }
    removeDeadEnds(result, sparceness);
    addRooms(result);
    removeDeadEnds(result, result.length * result[0].length);
    return result;
  }

  private void addRooms(int[][] cells) {
    for (int id = 2; id < maxRooms + 2; id++) {
      Room room = createRoom(id);
      int best = Integer.MAX_VALUE;
      Vector2D bestCell = null;
      for (Vector2D target : cellsList) {
        room.setLocation(target);
        int cost = room.cost(cells);
        if (cost > 0 && cost < best) {
          best = cost;
          bestCell = target;
        }
      }
      if (best < Integer.MAX_VALUE) {
        room.setLocation(bestCell);
        room.place(cells);
      }
    }
  }

  private Room createRoom(int id) {
    return new Room(random, random.nextInt(maxRoomHeight - minRoomHeight)
        + minRoomHeight, random.nextInt(maxRoomWidth - minRoomWidth)
        + minRoomWidth, id);
  }

  private void removeDeadEnds(int[][] cells, int maxSteps) {
    List<Vector2D> deadEnds = cellsList;
    for (int step = 0; step < maxSteps && !deadEnds.isEmpty(); step++) {
      List<Vector2D> ends = new LinkedList<Vector2D>();
      for (Vector2D current : deadEnds) {
        List<Vector2D> edges = getNeighbors(cells, current);
        if (edges.size() == 1) {
          cells[current.getX()][current.getY()] = 0;
          ends.add(edges.get(0));
        }
      }
      deadEnds = ends;
    }
  }

  private List<Vector2D> getNeighbors(int[][] cells, Vector2D target) {
    List<Vector2D> edges = new LinkedList<Vector2D>();
    for (Vector2D direction : DIRECTIONS) {
      Vector2D location = target.add(direction);
      int x = location.getX();
      int y = location.getY();
      if (x > 0 && x < cells.length && y > 0 && y < cells[x].length
          && cells[x][y] > 0) {
        edges.add(location);
      }
    }
    return edges;
  }

  public void toString(int[][] cells) {
    StringBuffer result = new StringBuffer();
    for (int x = 0; x < cells.length; x++) {
      for (int y = 0; y < cells[x].length; y++) {
        if (cells[x][y] == 100) {
          result.append("+");
        } else if (cells[x][y] > 0) {
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
    new BuckDungeonGenerator().toString(new BuckDungeonGenerator().generate());
  }

}
