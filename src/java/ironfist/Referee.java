package ironfist;

import ironfist.generator.RecursiveDungeonGenerator;
import ironfist.math.Vector;
import ironfist.persistence.Persistence;
import ironfist.persistence.PersistenceException;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Referee {

  private static final String CREATURE = "creature";
  private static final StairsPredicate stairsPredicate;
  private static final RecursiveDungeonGenerator generator;
  private static boolean run;

  static {
    generator = new RecursiveDungeonGenerator(System.currentTimeMillis());
    stairsPredicate = new StairsPredicate();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  static public boolean run(Creature creature) {
    run = true;

    while (run) {
      Level level = creature.getLevel();
      level.run();

      if (!level.isActive()) {
        try {
          Persistence.put(level.getName(), level);
        } catch (Exception e) {
        }
      }
    }

    return run;
  }

  /**
   * DOCUMENT ME!
   */
  static public void stop() {
    run = false;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   */
  public static void down(Creature creature) {
    Vector sourceLocation = creature.getCoordinate();
    Level sourceLevel = creature.getLevel();
    Tile tile = sourceLevel.get(sourceLocation);
    TileType type = tile.getTileType();

    if (type instanceof Floor) {
      Floor floor = (Floor) type;
      Portal portal = floor.getPortal();

      if (portal instanceof Stairs) {
        Stairs sourceStairs = (Stairs) portal;

        if (Stairs.DOWN.equals(sourceStairs.getDirection())) {
          Vector targetLocation = sourceStairs.getCoordinate();
          String targetName = sourceStairs.getLevelName();
          Tile targetTile = null;
          Level targetLevel = null;

          if (targetName == null) {
            String sourceName = sourceLevel.getName();
            targetName = Integer.toString(Integer.parseInt(sourceName) + 1);
            targetLevel = generator.generate(targetName);
            stairsPredicate.setDirection(Stairs.UP);
            targetTile = targetLevel.getRandom(stairsPredicate);

            Stairs targetStairs = (Stairs) ((Floor) targetTile.getTileType()).getPortal();
            targetStairs.setCoordinate(sourceLocation);
            targetStairs.setLevelName(sourceName);
            targetLocation = targetTile.getCoordinate();
            sourceStairs.setCoordinate(targetLocation);
            sourceStairs.setLevelName(targetName);
          } else {
            try {
              targetLevel = (Level) Persistence.get(targetName);
            } catch (Exception e) {
            }

            targetTile = targetLevel.get(targetLocation);
          }

          floor.setCreature(null);
          ((Floor) targetTile.getTileType()).setCreature(creature);
          creature.setCoordinate(targetLocation);
          creature.setLevel(targetLevel);
          targetLevel.setActive(true);
          sourceLevel.setActive(false);
        }
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   */
  public static void up(Creature creature) {
    Vector sourceLocation = creature.getCoordinate();
    Level sourceLevel = creature.getLevel();
    Tile tile = sourceLevel.get(sourceLocation);
    TileType type = tile.getTileType();

    if (type instanceof Floor) {
      Floor floor = (Floor) type;
      Portal portal = floor.getPortal();

      if (portal instanceof Stairs) {
        Stairs sourceStairs = (Stairs) portal;

        if (Stairs.UP.equals(sourceStairs.getDirection())) {
          Vector targetLocation = sourceStairs.getCoordinate();
          String targetName = sourceStairs.getLevelName();
          Tile targetTile = null;
          Level targetLevel = null;

          if (targetName == null) {
            String sourceName = sourceLevel.getName();
            targetName = Integer.toString(Integer.parseInt(sourceName) + 1);
            targetLevel = generator.generate(targetName);
            stairsPredicate.setDirection(Stairs.DOWN);
            targetTile = targetLevel.getRandom(stairsPredicate);

            Stairs targetStairs = (Stairs) ((Floor) targetTile.getTileType()).getPortal();
            targetStairs.setCoordinate(sourceLocation);
            targetStairs.setLevelName(sourceName);
            targetLocation = targetTile.getCoordinate();
            sourceStairs.setCoordinate(targetLocation);
            sourceStairs.setLevelName(targetName);
          } else {
            try {
              targetLevel = (Level) Persistence.get(targetName);
            } catch (Exception e) {
            }

            targetTile = targetLevel.get(targetLocation);
          }

          floor.setCreature(null);
          ((Floor) targetTile.getTileType()).setCreature(creature);
          creature.setCoordinate(targetLocation);
          creature.setLevel(targetLevel);
          targetLevel.setActive(true);
          sourceLevel.setActive(false);
        }
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   * @param direction
   *          DOCUMENT ME!
   */
  static public void move(Creature creature, Vector direction) {
    Vector oldCoordinate = creature.getCoordinate();
    Vector coordinate = oldCoordinate.add(direction);
    Level level = creature.getLevel();
    Tile tile = level.get(coordinate);
    TileType type = tile.getTileType();

    if (type instanceof Floor) {
      Floor floor = (Floor) type;
      Door door = floor.getDoor();

      if ((door == null) || door.isOpen()) {
        creature.setCoordinate(coordinate);
        floor.setCreature(creature);

        Tile oldTile = level.get(oldCoordinate);
        Floor oldFloor = (Floor) oldTile.getTileType();
        oldFloor.setCreature(null);
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   * @param thing
   *          DOCUMENT ME!
   */
  static public void pickup(Creature creature, Thing thing) {
    Vector coordinate = creature.getCoordinate();
    Level level = creature.getLevel();
    Tile tile = level.get(coordinate);
    TileType type = tile.getTileType();

    if (type instanceof Floor) {
      Floor floor = (Floor) type;
      creature.addThing(thing);
      thing.setOwner(creature);
      floor.removeThing(thing);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   * @param thing
   *          DOCUMENT ME!
   */
  static public void drop(Creature creature, Thing thing) {
    Vector coordinate = creature.getCoordinate();
    Level level = creature.getLevel();
    Tile tile = level.get(coordinate);
    TileType type = tile.getTileType();

    if (type instanceof Floor) {
      Floor floor = (Floor) type;
      creature.removeThing(thing);
      thing.setOwner(null);
      floor.addThing(thing);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   * @param door
   *          DOCUMENT ME!
   */
  static public void open(Creature creature, Door door) {
    if ((door != null) && !door.isOpen()) {
      door.open();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   * @param door
   *          DOCUMENT ME!
   */
  static public void close(Creature creature, Door door) {
    if ((door != null) && door.isOpen()) {
      door.close();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   */
  static public void quit(Creature creature) {
    Persistence.delete(creature.getName());
    stop();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param name
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  static public Creature load(String name) {
    Creature result = null;

    try {
      Persistence.open(name);

      result = (Creature) Persistence.get(CREATURE);
    } catch (PersistenceException e) {
      e.printStackTrace();
    }

    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param creature
   *          DOCUMENT ME!
   */
  static public void save(Creature creature) {
    try {
      Persistence.put(CREATURE, creature);
    } catch (PersistenceException e) {
      e.printStackTrace();
    }

    stop();
  }

}