package ironfist.level.dungeon.buck;

import ironfist.level.Region;
import ironfist.level.RegionFactory;
import ironfist.level.maze.prim.PrimMazeGenerator;
import ironfist.loop.Level;
import ironfist.math.Vector2D;
import ironfist.util.Loop;
import ironfist.util.MersenneTwister;

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
  private RegionFactory mazeFactory = new PrimMazeGenerator(random, 20, 80);
  private RegionFactory roomFactory = new RoomGenerator(random, 5, 9, 9, 14, 2);
  private int sparceness = 7;
  private int maxRooms = 12;

  private List<Vector2D> cells;

  public Level generate() {
    Level result = new Level(new Object[20][80]);
    cells = new LinkedList<Vector2D>();
    for (int x = 0; x < 20; x++) {
      for (int y = 0; y < 80; y++) {
        result.set(Vector2D.get(x, y), 0);
        cells.add(Vector2D.get(x, y));
      }
    }

    Region maze = mazeFactory.create();
    maze.setLocation(Vector2D.get(0, 0));
    maze.place(result);

    removeDeadEnds(result, sparceness);

    addRooms(result);

    removeDeadEnds(result, cells.size());

    center(result);

    return result;
  }

  private void center(Level level) {
    int north = level.getLength() / 2;
    int south = level.getLength() / 2;
    int east = level.getWidth() / 2;
    int west = level.getWidth() / 2;
    for (Vector2D current : cells) {
      if ((Integer) level.get(current) > 0) {
        int x = current.getX();
        int y = current.getY();
        north = Math.min(north, x - 1);
        south = Math.max(south, x + 1);
        west = Math.min(west, y - 1);
        east = Math.max(east, y + 1);
      }
    }
    System.out.println("north=" + north + ",south=" + south + ",east=" + east
        + ",west=" + west);
  }

  private void addRooms(Level level) {
    for (int count = 0; count < maxRooms; count++) {
      Region room = roomFactory.create();

      SortedMap<Integer, Vector2D> map = new TreeMap<Integer, Vector2D>();
      new Loop<Vector2D>(cells).forEach(new CollectCost(room, level, map));

      Integer key = new Loop<Integer>(map.keySet()).detect(new GetBestCost());

      if (key != null) {
        room.setLocation(map.get(key));
        room.place(level);
      }
    }
  }

  private void removeDeadEnds(Level level, int maxSteps) {
    List<Vector2D> deadEnds = cells;
    for (int step = 0; step < maxSteps && !deadEnds.isEmpty(); step++) {
      List<Vector2D> ends = new LinkedList<Vector2D>();
      for (Vector2D current : deadEnds) {
        List<Vector2D> edges = getEdges(level, current);
        if (edges.size() == 1) {
          level.set(current, 0);
          ends.add(edges.get(0));
        }
      }
      deadEnds = ends;
    }
  }

  private List<Vector2D> getEdges(Level level, Vector2D target) {
    List<Vector2D> edges = new LinkedList<Vector2D>();
    for (Vector2D direction : DIRECTIONS) {
      Vector2D location = target.add(direction);
      if (level.contains(location) && (Integer) level.get(location) > 0) {
        edges.add(location);
      }
    }
    return edges;
  }

  public static void main(String[] args) {
    System.out.println(new BuckDungeonGenerator().generate());
  }

}
