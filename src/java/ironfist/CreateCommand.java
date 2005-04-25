package ironfist;

import ironfist.generator.HomeGenerator;
import ironfist.generator.IsTileType;
import ironfist.persistence.Persistence;
import ironfist.persistence.PersistenceException;

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