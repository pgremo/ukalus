package ironfist.generator;

import ironfist.Door;
import ironfist.Floor;
import ironfist.Level;
import ironfist.Stairs;
import ironfist.Tile;
import ironfist.TileType;
import ironfist.Wall;

import ironfist.math.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class RecursiveDungeonGenerator {

  private static final TileTypeDirectionPredicate directionPredicate = new TileTypeDirectionPredicate();
  private static final TileTypePredicate tilePredicate = new TileTypePredicate();
  private static final EmptyFloorTilePredicate doorPredicate = new EmptyFloorTilePredicate();
  private static final DungeonRoomComparator roomComparator = new DungeonRoomComparator();
  private static final int MAX_ROOM_HEIGHT = 10;
  private static final int MAX_ROOM_WIDTH = 12;
  private static final int MIN_ROOM_HEIGHT = 5;
  private static final int MIN_ROOM_WIDTH = 5;
  private static final Vector[] directions = {
      new Vector(-1, 0),
      new Vector(0, 1),
      new Vector(1, 0),
      new Vector(0, -1)};
  private static final Class floor = Floor.class;
  private static final Class terminal = Terminal.class;
  private static final Class wall = Wall.class;
  private static final Class barrier = Barrier.class;
  private RectangleRoomFactory areaFactory;
  private PassageFactory connectorFactory;
  private Random randomizer;
  private Level level;

  {
    connectorFactory = new PassageFactory();
    connectorFactory.setFloorClass(floor);
    connectorFactory.setWallClass(wall);
    connectorFactory.setCornerClass(barrier);
    connectorFactory.setTerminalClass(terminal);
    areaFactory = new RectangleRoomFactory();
    areaFactory.setMinRoomHeight(MIN_ROOM_HEIGHT);
    areaFactory.setMinRoomWidth(MIN_ROOM_WIDTH);
    areaFactory.setMaxRoomHeight(MAX_ROOM_HEIGHT);
    areaFactory.setMaxRoomWidth(MAX_ROOM_WIDTH);
    areaFactory.setFloorClass(floor);
    areaFactory.setWallClass(wall);
    areaFactory.setCornerClass(barrier);
  }

  public RecursiveDungeonGenerator(long seed) {
    randomizer = new Random(seed);
    connectorFactory.setRandomizer(randomizer);
    areaFactory.setRandomizer(randomizer);
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Level generate(String name) {
    level = new Level(name);

    int areaMax = 15;
    int tries = 0;
    List list = areaFactory.create();
    int largestX = 0;
    int largestY = 0;
    Iterator iterator = list.iterator();

    while (iterator.hasNext()) {
      Vector current = ((Tile) iterator.next()).getCoordinate();

      if (largestX < current.getX()) {
        largestX = (int) current.getX();
      }

      if (largestY < current.getY()) {
        largestY = (int) current.getY();
      }
    }

    List areas = new ArrayList();
    Area target = new Area(list);
    int x = randomizer.nextInt(level.getHeight() - largestX - 1);
    int y = randomizer.nextInt(level.getWidth() - largestY - 1);
    target.setCoordinate(new Vector(x, y));
    areas.add(target);
    target.place(level);

    while ((areas.size() < areaMax) && (tries < (areaMax * 10))) {
      target = new Area(areaFactory.create());

      Area source = (Area) areas.get(randomizer.nextInt(areas.size()));

      if (connectAreas(source, target)) {
        areas.add(target);
      } else {
        tries++;
      }
    }

    doorPredicate.setTileTypeClass(floor);

    Area upRegion = (Area) areas.get(0);
    Tile upLocation = upRegion.getRandom(doorPredicate);
    ((Floor) upLocation.getTileType()).setPortal(new Stairs(Stairs.UP));

    Area downRegion = (Area) areas.get(randomizer.nextInt(areas.size() - 2) + 1);
    Tile downLocation = downRegion.getRandom(doorPredicate);
    ((Floor) downLocation.getTileType()).setPortal(new Stairs(Stairs.DOWN));

    return level;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param source
   *          DOCUMENT ME!
   * @param target
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private boolean connectAreas(Area source, Area target) {
    boolean result = false;
    Tile sourceDoor;
    Tile targetDoor;
    Area connector;

    int tries = 0;
    int maxTries = 10;

    do {
      tilePredicate.setTileTypeClass(wall);

      Vector sourceSide;

      do {
        sourceDoor = source.getRandom(tilePredicate);
        connectorFactory.setBaseLength(tries);
        sourceSide = getSide(source, sourceDoor.getCoordinate());

        Vector originalSide = sourceSide;

        if (randomizer.nextBoolean()) {
          sourceSide = sourceSide.orthoganal();

          if (randomizer.nextBoolean()) {
            sourceSide = sourceSide.multiply(-1);
          }
        }

        connectorFactory.setDirection(sourceSide);

        connector = new Area(connectorFactory.create());
        connector.setCoordinate(sourceDoor.getCoordinate()
          .add(originalSide)
          .subtract(sourceSide.multiply(2))
          .add(source.getCoordinate()));
        tries++;
      } while (!connector.check(level, roomComparator) && (tries < maxTries));

      tilePredicate.setTileTypeClass(terminal);

      Vector lastLocationCoordinate = connector.getRandom(tilePredicate)
        .getCoordinate();

      directionPredicate.setTileTypeClass(wall);
      directionPredicate.setArea(target);
      directionPredicate.setDirection(getSide(connector, lastLocationCoordinate).multiply(
        -1));

      targetDoor = target.getRandom(directionPredicate);

      Vector connectorCoordinate = connector.getCoordinate();
      Vector total = connectorCoordinate.add(lastLocationCoordinate)
        .subtract(targetDoor.getCoordinate());

      target.setCoordinate(total);

      tries++;
    } while (!target.check(level, roomComparator) && (tries < maxTries));

    if (tries < maxTries) {
      Floor doorWay = new Floor();
      doorWay.setDoor(new Door(null, randomizer.nextBoolean()));
      sourceDoor.setTileType(doorWay);
      doorWay = new Floor();
      doorWay.setDoor(new Door(null, randomizer.nextBoolean()));
      targetDoor.setTileType(doorWay);
      connector.place(level);
      source.place(level);
      target.place(level);
      result = true;
    }

    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param region
   *          DOCUMENT ME!
   * @param coordinate
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private Vector getSide(Area region, Vector coordinate) {
    Vector result = null;

    for (int index = 0; (index < 4) && (result == null); index++) {
      if (region.get(coordinate.add(directions[index])) == null) {
        result = directions[index];
      }
    }

    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param args
   *          DOCUMENT ME!
   * 
   * @throws Exception
   *           DOCUMENT ME!
   */
  public static void main(String[] args) throws Exception {
    long seed = System.currentTimeMillis();
    RecursiveDungeonGenerator generator = new RecursiveDungeonGenerator(seed);
    Level dungeon = generator.generate("1");
    StringBuffer output = new StringBuffer();

    for (int x = 0; x < dungeon.getHeight(); x++) {
      for (int y = 0; y < dungeon.getWidth(); y++) {
        Tile current = dungeon.get(new Vector(x, y));
        TileType type = null;

        if (current != null) {
          type = current.getTileType();
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