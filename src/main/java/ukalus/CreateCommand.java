package ukalus;

import ukalus.level.dungeon.recursive.HomeGenerator;
import ukalus.level.dungeon.recursive.IsTileType;
import ukalus.persistence.Persistence;
import ukalus.persistence.PersistenceException;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class CreateCommand {

  /**
   * DOCUMENT ME!
   * 
   * @param client
   *          DOCUMENT ME!
   */
  public Object execute(String name) {
    try {
      Persistence.create(name);
    } catch (PersistenceException e) {
      e.printStackTrace();
    }

    Creature creature = new Creature();
    creature.setName(name);

    HomeGenerator generator = new HomeGenerator();
    Level level = generator.generate("0");
    IsTileType predicate = new IsTileType(Floor.class);

    Tile tile = level.getRandom(predicate);
    Floor floor = (Floor) tile.getTileType();
    floor.setCreature(creature);
    creature.setCoordinate(tile.getLocation());
    creature.setLevel(level);
    return creature;
  }
}