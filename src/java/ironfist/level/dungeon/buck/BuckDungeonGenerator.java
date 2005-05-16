package ironfist.level.dungeon.buck;

import ironfist.level.Region;
import ironfist.level.maze.MazeGenerator;
import ironfist.level.maze.prim.PrimMazeGenerator;
import ironfist.math.Vector2D;
import ironfist.util.MersenneTwister;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class BuckDungeonGenerator {

  private static final Vector2D[] DIRECTIONS = new Vector2D[]{
      Vector2D.get(1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(0, -1),
      Vector2D.get(-1, 0)};

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
    int[][] result = new int[20][80];
    cellsList = new LinkedList<Vector2D>();
    for (int x = 0; x < result.length; x++) {
      for (int y = 0; y < result[x].length; y++) {
        cellsList.add(Vector2D.get(x, y));
      }
    }

    Region maze = generator.generate();
    maze.setLocation(Vector2D.get(0, 0));
    maze.place(result);

    removeDeadEnds(result, sparceness);

    addRooms(result);

    removeDeadEnds(result, result.length * result[0].length);

    center(result);

    return result;
  }

  private void center(int[][] cells) {
    int north = cells.length / 2;
    int south = cells.length / 2;
    int east = cells[0].length / 2;
    int west = cells[0].length / 2;
    for (int x = 0; x < cells.length; x++) {
      for (int y = 0; y < cells[x].length; y++) {
        if (cells[x][y] > 0) {
          north = Math.min(north, x - 1);
          south = Math.max(south, x + 1);
          west = Math.min(west, y - 1);
          east = Math.max(east, y + 1);
        }
      }
    }
    System.out.println("north=" + north + ",south=" + south + ",east=" + east
        + ",west=" + west);
  }

  private void addRooms(int[][] cells) {
    for (int id = 2; id < maxRooms + 2; id++) {
      SortedMap<Integer, Vector2D> map = new TreeMap<Integer, Vector2D>();
      Region room = createRoom(id);
      for (Vector2D target : cellsList) {
        room.setLocation(target);
        map.put(room.cost(cells), target);
      }
      boolean found = false;
      Iterator<Integer> iterator = map.keySet()
        .iterator();
      while (iterator.hasNext() && !found) {
        int first = iterator.next();
        if (first > 0 && first < Integer.MAX_VALUE) {
          room.setLocation(map.get(first));
          room.place(cells);
          found = true;
        }
      }
    }
  }

  private Region createRoom(int id) {
    return new Room(random, random.nextInt(maxRoomHeight - minRoomHeight)
        + minRoomHeight, random.nextInt(maxRoomWidth - minRoomWidth)
        + minRoomWidth, id);
  }

  private void removeDeadEnds(int[][] cells, int maxSteps) {
    List<Vector2D> deadEnds = cellsList;
    for (int step = 0; step < maxSteps && !deadEnds.isEmpty(); step++) {
      List<Vector2D> ends = new LinkedList<Vector2D>();
      for (Vector2D current : deadEnds) {
        List<Vector2D> edges = getEdges(cells, current);
        if (edges.size() == 1) {
          cells[current.getX()][current.getY()] = 0;
          ends.add(edges.get(0));
        }
      }
      deadEnds = ends;
    }
  }

  private List<Vector2D> getEdges(int[][] cells, Vector2D target) {
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

  public static void toString(int[][] cells) {
    StringBuffer result = new StringBuffer();
    for (int x = 0; x < cells.length; x++) {
      for (int y = 0; y < cells[x].length; y++) {
        if (cells[x][y] == 100) {
          result.append("+");
        } else if (cells[x][y] > 0) {
          result.append(" ");
        } else {
          result.append("#");
        }
      }
      result.append("\n");
    }
    System.out.print(result);
  }

  public static void main(String[] args) {
    toString(new BuckDungeonGenerator().generate());
  }

}
