package ukalus;

import ukalus.math.Vector2D;
import ukalus.util.Closure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Level implements Serializable {

  private static final long serialVersionUID = 3834023645663016245L;
  private static Roll roll;

  static {
    roll = new Roll();
  }

  private String name;
  private Tile[][] tiles;
  private int height = 20;
  private int width = 80;
  private Random randomizer;
  private boolean active;

  {
    tiles = new Tile[height][width];
    randomizer = new Random();
  }

  /**
   * Creates a new Level object.
   * 
   * @param name
   *          DOCUMENT ME!
   */
  public Level(String name) {
    this.name = name;
  }

  /**
   * DOCUMENT ME!
   */
  public void run() {
    List<InitiativeCell> list = new LinkedList<InitiativeCell>();

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        if ((tiles[x][y] != null) && tiles[x][y].getTileType() instanceof Floor) {
          Creature current = ((Floor) tiles[x][y].getTileType()).getCreature();

          if (current != null) {
            // REDTAG - invented quickness. Need to get from creature.
            int initiative = roll.roll(3);

            do {
              list.add(new InitiativeCell(initiative, current));
              initiative -= 20;
            } while (initiative > 0);
          }
        }
      }
    }

    Collections.sort(list, new ReverseIntegerComparator());

    for (int index = 0; index < list.size(); index++) {
      Creature creature = list.get(index)
        .getCreature();

      if (creature.getLevel()
        .equals(this)) {
        creature.activate();
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getHeight() {
    return height;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getWidth() {
    return width;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param coordinate
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Tile get(Vector2D coordinate) {
    int x = coordinate.getX();
    int y = coordinate.getY();
    Tile result = null;

    if ((x < 0) || (x > height - 1) || (y < 0) || (y > width - 1)) {
      result = null;
    } else {
      result = tiles[x][y];
    }

    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param predicate
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Tile getRandom(Closure<Tile, Boolean> predicate) {
    Tile result = null;
    List<Tile> candidates = new ArrayList<Tile>();

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        if (predicate.apply(tiles[x][y])) {
          candidates.add(tiles[x][y]);
        }
      }
    }

    if (candidates.size() > 0) {
      result = candidates.get(randomizer.nextInt(candidates.size()));
    }

    return result;
  }

  public void set(Vector2D coordinate, TileType type) {
    if (type == null) {
      tiles[coordinate.getX()][coordinate.getY()] = null;
    } else {
      tiles[coordinate.getX()][coordinate.getY()] = new Tile(
        coordinate, type);
    }
  }

  /**
   * Returns the name.
   * 
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the active.
   * 
   * @return boolean
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets the active.
   * 
   * @param active
   *          The active to set
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean contains(Vector2D currentCoordinate) {
    return (currentCoordinate.getX() >= 0)
        && (currentCoordinate.getX() < getHeight() - 1)
        && (currentCoordinate.getY() >= 0)
        && (currentCoordinate.getY() < getWidth() - 1);
  }

  private class InitiativeCell {

    private int initiative;
    private Creature creature;

    public InitiativeCell(int initiative, Creature creature) {
      this.initiative = initiative;
      this.creature = creature;
    }

    /**
     * Returns the creature.
     * 
     * @return Creature
     */
    public Creature getCreature() {
      return creature;
    }

    /**
     * Returns the initiative.
     * 
     * @return int
     */
    public int getInitiative() {
      return initiative;
    }
  }

  private class ReverseIntegerComparator implements Comparator<InitiativeCell> {

    /**
     * @see java.util.Comparator#compare(Object, Object)
     */
    public int compare(InitiativeCell o1, InitiativeCell o2) {
      int rank1 = o1.getInitiative();
      int rank2 = o2.getInitiative();

      return (rank1 < rank2) ? 1 : (rank1 > rank2) ? (-1) : 0;
    }
  }
}