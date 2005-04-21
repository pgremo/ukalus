package ironfist.generator;

import ironfist.Door;
import ironfist.Floor;
import ironfist.Level;
import ironfist.PassageFloor;
import ironfist.Stairs;
import ironfist.Tile;
import ironfist.TileType;
import ironfist.Wall;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecursiveDungeonGenerator {

  private static final int MAX_ROOM_HEIGHT = 10;
  private static final int MAX_ROOM_WIDTH = 12;
  private static final int MIN_ROOM_HEIGHT = 5;
  private static final int MIN_ROOM_WIDTH = 5;
  private static final Vector2D[] DIRECTIONS = {
      Vector2D.get(-1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(1, 0),
      Vector2D.get(0, -1)};
  private static final Class<? extends TileType> FLOOR = Floor.class;
  private static final Class<? extends TileType> TERMINAL = Terminal.class;
  private static final Class<? extends TileType> WALL = Wall.class;
  private static final Class<? extends TileType> BARRIER = Barrier.class;
  private RectangleRoomFactory areaFactory;
  private PassageFactory connectorFactory;
  private Random random;
  private Level level;

  {
    connectorFactory = new PassageFactory();
    connectorFactory.setFloorClass(PassageFloor.class);
    connectorFactory.setWallClass(WALL);
    connectorFactory.setCornerClass(BARRIER);
    connectorFactory.setTerminalClass(TERMINAL);
    areaFactory = new RectangleRoomFactory();
    areaFactory.setMinRoomHeight(MIN_ROOM_HEIGHT);
    areaFactory.setMinRoomWidth(MIN_ROOM_WIDTH);
    areaFactory.setMaxRoomHeight(MAX_ROOM_HEIGHT);
    areaFactory.setMaxRoomWidth(MAX_ROOM_WIDTH);
    areaFactory.setFloorClass(FLOOR);
    areaFactory.setWallClass(WALL);
    areaFactory.setCornerClass(BARRIER);
  }

  public RecursiveDungeonGenerator(long seed) {
    random = new Random(seed);
    connectorFactory.setRandomizer(random);
    areaFactory.setRandomizer(random);
  }

  public Level generate(String name) {
    level = new Level(name);

    int areaMax = 15;
    int tries = 0;
    List<Tile> list = areaFactory.create();
    int largestX = 0;
    int largestY = 0;

    for (Tile tile : list) {
      Vector2D current = tile.getCoordinate();
      largestX = Math.max(largestX, current.getX());
      largestY = Math.max(largestY, current.getY());
    }

    List<Area> areas = new ArrayList<Area>();
    Area target = new Area(list);
    int x = random.nextInt(level.getHeight() - largestX - 1);
    int y = random.nextInt(level.getWidth() - largestY - 1);
    target.setCoordinate(Vector2D.get(x, y));
    areas.add(target);
    target.place(level);

    while ((areas.size() < areaMax) && (tries < (areaMax * 10))) {
      target = new Area(areaFactory.create());

      Area source = areas.get(random.nextInt(areas.size()));

      if (connectAreas(source, target)) {
        areas.add(target);
      } else {
        tries++;
      }
    }
    EmptyFloorTilePredicate EMPTY_FLOOR_PREDICATE = new EmptyFloorTilePredicate(
      FLOOR);
    Area upRegion = areas.get(0);
    Tile upLocation = upRegion.getRandom(EMPTY_FLOOR_PREDICATE);
    ((Floor) upLocation.getTileType()).setPortal(new Stairs(Stairs.UP));

    Area downRegion = areas.get(random.nextInt(areas.size() - 2) + 1);
    Tile downLocation = downRegion.getRandom(EMPTY_FLOOR_PREDICATE);
    ((Floor) downLocation.getTileType()).setPortal(new Stairs(Stairs.DOWN));

    return level;
  }

  private boolean connectAreas(Area source, Area target) {
    boolean result = false;

    Area connector;
    Tile sourceDoor;
    Tile targetDoor;

    int tries = 0;
    int maxTries = 20;

    do {
      sourceDoor = source.getRandom(new TileTypePredicate(WALL));
      connectorFactory.setBaseLength(tries);
      Vector2D sourceSide = getSide(source, sourceDoor.getCoordinate());

      Vector2D originalSide = sourceSide;

      int flip = random.nextInt(3);
      if (flip > 0) {
        sourceSide = sourceSide.orthoganal();
      }
      if (flip == 2) {
        sourceSide = sourceSide.multiply(-1);
      }
      connectorFactory.setDirection(sourceSide);

      connector = new Area(connectorFactory.create());
      connector.setCoordinate(sourceDoor.getCoordinate()
        .add(originalSide)
        .subtract(sourceSide.multiply(2))
        .add(source.getCoordinate()));

      Vector2D lastLocationCoordinate = connector.getRandom(
        new TileTypePredicate(TERMINAL))
        .getCoordinate();

      targetDoor = target.getRandom(new TileTypeDirectionPredicate(WALL,
        getSide(connector, lastLocationCoordinate).multiply(-1), target));

      Vector2D connectorCoordinate = connector.getCoordinate();
      Vector2D total = connectorCoordinate.add(lastLocationCoordinate)
        .subtract(targetDoor.getCoordinate());

      target.setCoordinate(total);

      Closure<Tile, Boolean> passageComparator = new DungeonRoomPredicate(
        level, connectorCoordinate);
      Closure<Tile, Boolean> roomComparator = new DungeonRoomPredicate(level,
        total);

      result = connector.check(passageComparator)
          && target.check(roomComparator);
      if (!result) {
        tries++;
      }

    } while (!result && tries < maxTries);

    if (tries < maxTries) {
      Floor doorWay = new Floor();
      doorWay.setDoor(new Door(null, random.nextBoolean()));
      sourceDoor.setTileType(doorWay);
      doorWay = new Floor();
      doorWay.setDoor(new Door(null, random.nextBoolean()));
      targetDoor.setTileType(doorWay);
      connector.place(level);
      source.place(level);
      target.place(level);
      result = true;
    }

    return result;
  }

  private Vector2D getSide(Area area, Vector2D coordinate) {
    Vector2D result = null;
    int direction = random.nextInt(DIRECTIONS.length);
    for (int index = 0; index < DIRECTIONS.length; index++) {
      if (area.get(coordinate.add(DIRECTIONS[direction])) == null) {
        result = DIRECTIONS[direction];
      }
      direction = (direction + 1) % DIRECTIONS.length;
    }

    return result;
  }

  public static void main(String[] args) throws Exception {
    long seed = System.currentTimeMillis();
    RecursiveDungeonGenerator generator = new RecursiveDungeonGenerator(seed);
    Level dungeon = generator.generate("1");
    StringBuffer output = new StringBuffer();

    for (int x = 0; x < dungeon.getHeight(); x++) {
      for (int y = 0; y < dungeon.getWidth(); y++) {
        Tile cell = dungeon.get(Vector2D.get(x, y));
        TileType type = null;

        if (cell != null) {
          type = cell.getTileType();
        }

        if (type == null) {
          output.append(" ");
        } else if (type instanceof Wall) {
          output.append("#");
        } else {
          Floor floor = (Floor) type;

          if (floor.getDoor() != null) {
            output.append("+");
          } else if (floor.getPortal() != null) {
            if (((Stairs) floor.getPortal()).getDirection()
              .equals(Stairs.DOWN)) {
              output.append(">");
            } else if (((Stairs) floor.getPortal()).getDirection()
              .equals(Stairs.UP)) {
              output.append("<");
            }
          } else {
            output.append(".");
          }
        }
      }

      output.append("\n");
    }

    System.out.println(output.toString());
  }
}