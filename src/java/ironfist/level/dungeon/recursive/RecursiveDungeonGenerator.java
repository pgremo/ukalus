package ironfist.level.dungeon.recursive;

import ironfist.Door;
import ironfist.Floor;
import ironfist.Level;
import ironfist.Stairs;
import ironfist.Tile;
import ironfist.TileType;
import ironfist.Wall;
import ironfist.math.Vector2D;
import ironfist.util.Loop;
import ironfist.util.MersenneTwister;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecursiveDungeonGenerator {

  private static final int MAX_AREAS = 15;
  private static final int MAX_TRIES = 20;
  private static final int MAX_ROOM_HEIGHT = 5;
  private static final int MAX_ROOM_WIDTH = 12;
  private static final int MIN_ROOM_HEIGHT = 5;
  private static final int MIN_ROOM_WIDTH = 7;
  private static final Class<? extends TileType> FLOOR = Floor.class;
  private static final Class<? extends TileType> TERMINAL = Terminal.class;
  private static final Class<? extends TileType> WALL = Wall.class;
  private RectangleRoomFactory areaFactory;
  private PassageFactory connectorFactory;
  private Random random;
  private Level level;

  public RecursiveDungeonGenerator(Random random) {
    this.random = random;
    connectorFactory = new PassageFactory(random, FLOOR, WALL, TERMINAL, 20);
    areaFactory = new RectangleRoomFactory(random, FLOOR, WALL, TERMINAL,
      MAX_ROOM_HEIGHT, MAX_ROOM_WIDTH, MIN_ROOM_HEIGHT, MIN_ROOM_WIDTH);
  }

  public Level generate(String name) {
    level = new Level(name);

    List<Tile> list = areaFactory.create();
    Area target = new Area(list);

    Dimension dimension = new Dimension();
    new Loop<Tile>(list).forEach(dimension);

    int x = random.nextInt(level.getHeight() - dimension.getHeight() - 1);
    int y = random.nextInt(level.getWidth() - dimension.getWidth() - 1);
    target.setCoordinate(Vector2D.get(x, y));
    List<Area> areas = new ArrayList<Area>();
    areas.add(target);
    target.place(level);

    for (int tries = 0; areas.size() < MAX_AREAS; tries++) {
      target = new Area(areaFactory.create());
      Area source = areas.get(random.nextInt(areas.size()));
      if (connectAreas(source, target)) {
        areas.add(target);
      }
    }

    EmptyFloorTilePredicate emptyFloor = new EmptyFloorTilePredicate();
    Area upRegion = areas.get(0);
    Tile upLocation = upRegion.getRandom(emptyFloor, random);
    ((Floor) upLocation.getTileType()).setPortal(new Stairs(Stairs.UP));

    Area downRegion = areas.get(random.nextInt(areas.size() - 2) + 1);
    Tile downLocation = downRegion.getRandom(emptyFloor, random);
    ((Floor) downLocation.getTileType()).setPortal(new Stairs(Stairs.DOWN));

    return level;
  }

  private boolean connectAreas(Area source, Area target) {
    Area connector = null;
    Tile enter = null;
    Tile exit = null;
    IsTileType terminalPredicate = new IsTileType(TERMINAL);

    boolean result = false;
    for (int tries = MAX_TRIES; tries > 0 && !result; tries--) {
      enter = source.getRandom(terminalPredicate, random);
      Vector2D direction = source.getSide(enter.getLocation());
      Vector2D originalDirection = direction;

      int flip = random.nextInt(3);
      if (flip > 0) {
        direction = direction.orthoganal();
      }
      if (flip == 2) {
        direction = direction.multiply(-1);
      }

      connector = new Area(connectorFactory.create());
      connector.rotate(direction);
      connector.setCoordinate(enter.getLocation()
        .add(originalDirection)
        .subtract(direction.multiply(2))
        .add(source.getCoordinate()));

      Vector2D lastLocationCoordinate = connector.getRandom(terminalPredicate,
        random)
        .getLocation();

      exit = target.getRandom(new TileTypeDirectionPredicate(TERMINAL,
        connector.getSide(lastLocationCoordinate)
          .multiply(-1), target), random);

      Vector2D connectorCoordinate = connector.getCoordinate();

      Vector2D targetCoordinate = connectorCoordinate.add(
        lastLocationCoordinate)
        .subtract(exit.getLocation());
      target.setCoordinate(targetCoordinate);

      result = connector.check(new DungeonRoomPredicate(level,
        connectorCoordinate))
          && target.check(new DungeonRoomPredicate(level, targetCoordinate));
    }

    if (result) {
      Floor doorWay = new Floor();
      doorWay.setDoor(new Door(null, random.nextBoolean()));
      enter.setTileType(doorWay);

      doorWay = new Floor();
      doorWay.setDoor(new Door(null, random.nextBoolean()));
      exit.setTileType(doorWay);

      connector.place(level);
      source.place(level);
      target.place(level);
    }

    return result;
  }

  public static void main(String[] args) throws Exception {
    long seed = System.currentTimeMillis();
    RecursiveDungeonGenerator generator = new RecursiveDungeonGenerator(
      new MersenneTwister(seed));
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