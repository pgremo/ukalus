package ukalus.level.dungeon.buck;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;
import ukalus.level.Level;
import ukalus.level.Region;
import ukalus.level.RegionFactory;
import ukalus.level.maze.prim.PrimMazeGenerator;
import ukalus.math.Vector2D;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class BuckDungeonGenerator {

  private static final Vector2D[] DIRECTIONS = new Vector2D[]{
    Vector2D.get(1, 0),
    Vector2D.get(0, 1),
    Vector2D.get(0, -1),
    Vector2D.get(-1, 0)};

  private Random random = new RandomAdaptor(new MersenneTwister());
  private RegionFactory<Integer> mazeFactory = new PrimMazeGenerator(random, 20, 80);
  private RegionFactory<Integer> roomFactory = new RoomGenerator(random, 5, 9, 9, 14, 2);
  private int sparceness = 7;
  private int maxRooms = 12;

  private List<Vector2D> cells;

  public Level generate() {
    Level<Integer> level = new Level<>(new Integer[20][80]);
    cells = new LinkedList<>();
    for (int x = 0; x < 20; x++) {
      for (int y = 0; y < 80; y++) {
        level.set(Vector2D.get(x, y), 0);
        cells.add(Vector2D.get(x, y));
      }
    }

    Region<Integer> maze = mazeFactory.create();
    maze.place(Vector2D.get(0, 0), level);

    removeDeadEnds(level, sparceness);

    addRooms(level);

    removeDeadEnds(level, cells.size());

    return level;
  }

  private void addRooms(Level<Integer> level) {
    for (int count = 0; count < maxRooms; count++) {
      Region<Integer> room = roomFactory.create();

      cells.stream()
        .map(x -> new SimpleEntry<>(room.cost(x, level), x))
        .filter(x -> 0 < x.getKey() && x.getKey() < Integer.MAX_VALUE)
        .min(comparingInt(SimpleEntry::getKey))
        .ifPresent(x -> room.place(x.getValue(), level));
    }
  }

  private void removeDeadEnds(Level<Integer> level, int maxSteps) {
    List<Vector2D> deadEnds = new LinkedList<Vector2D>(cells);
    for (int step = 0; step < maxSteps && !deadEnds.isEmpty(); step++) {
      List<Vector2D> ends = new LinkedList<Vector2D>();
      for (Vector2D current : deadEnds) {
        List<Vector2D> edges = Arrays.stream(DIRECTIONS)
          .map(current::add)
          .filter(x -> level.contains(x) && level.get(x) > 0)
          .collect(toList());

        if (edges.size() == 1) {
          level.set(current, 0);
          ends.add(edges.get(0));
        }
      }
      deadEnds = ends;
    }
  }

  public static void main(String[] args) {
    System.out.print(new BuckDungeonGenerator().generate());
  }
}
