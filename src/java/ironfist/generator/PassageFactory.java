package ironfist.generator;

import ironfist.Tile;
import ironfist.TileType;
import ironfist.geometry.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class PassageFactory {
  private Random randomizer;
  private Vector direction;
  private int baseLength;
  private Class floorClass;
  private Class wallClass;
  private Class cornerClass;
  private Class terminalClass;

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public List create() {
    LinkedList list = new LinkedList();
    int length = 3 + randomizer.nextInt(4) + 
                 ((randomizer.nextInt(5) == 0) ? randomizer.nextInt(5) : 0) + 
                 baseLength;
    Vector right = direction.orthoganal();
    Vector left = right.multiply(-1);

    try {
      list.add(
          new Tile(right.add(direction), (TileType) cornerClass.newInstance()));
      list.add(new Tile(direction, (TileType) wallClass.newInstance()));
      list.add(
          new Tile(left.add(direction), (TileType) cornerClass.newInstance()));

      for (int index = 2; index < (length - 1); index++) {
        list.add(
            new Tile(right.add(direction.multiply(index)), 
                     (TileType) (TileType) cornerClass.newInstance()));
        list.add(
            new Tile(direction.multiply(index), 
                     (TileType) floorClass.newInstance()));
        list.add(
            new Tile(left.add(direction.multiply(index)), 
                     (TileType) wallClass.newInstance()));
      }

      list.add(
          new Tile(right.add(direction.multiply(length - 1)), 
                   (TileType) terminalClass.newInstance()));
      list.add(
          new Tile(direction.multiply(length - 1), 
                   (TileType) floorClass.newInstance()));
      list.add(
          new Tile(left.add(direction.multiply(length - 1)), 
                   (TileType) terminalClass.newInstance()));

      list.add(
          new Tile(right.add(direction.multiply(length)), 
                   (TileType) cornerClass.newInstance()));
      list.add(
          new Tile(direction.multiply(length), 
                   (TileType) terminalClass.newInstance()));
      list.add(
          new Tile(left.add(direction.multiply(length)), 
                   (TileType) cornerClass.newInstance()));
    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    }

    return list;
  }

  /**
   * Returns the baseLength.
   * 
   * @return int
   */
  public int getBaseLength() {
    return baseLength;
  }

  /**
   * Returns the direction.
   * 
   * @return Coordinate
   */
  public Vector getDirection() {
    return direction;
  }

  /**
   * Sets the baseLength.
   * 
   * @param baseLength The baseLength to set
   */
  public void setBaseLength(int baseLength) {
    this.baseLength = baseLength;
  }

  /**
   * Sets the direction.
   * 
   * @param direction The direction to set
   */
  public void setDirection(Vector direction) {
    this.direction = direction;
  }

  /**
   * Returns the cornerClass.
   * 
   * @return Class
   */
  public Class getCornerClass() {
    return cornerClass;
  }

  /**
   * Returns the floorClass.
   * 
   * @return Class
   */
  public Class getFloorClass() {
    return floorClass;
  }

  /**
   * Returns the terminalClass.
   * 
   * @return Class
   */
  public Class getTerminalClass() {
    return terminalClass;
  }

  /**
   * Returns the wallClass.
   * 
   * @return Class
   */
  public Class getWallClass() {
    return wallClass;
  }

  /**
   * Sets the cornerClass.
   * 
   * @param cornerClass The cornerClass to set
   */
  public void setCornerClass(Class cornerClass) {
    this.cornerClass = cornerClass;
  }

  /**
   * Sets the floorClass.
   * 
   * @param floorClass The floorClass to set
   */
  public void setFloorClass(Class floorClass) {
    this.floorClass = floorClass;
  }

  /**
   * Sets the terminalClass.
   * 
   * @param terminalClass The terminalClass to set
   */
  public void setTerminalClass(Class terminalClass) {
    this.terminalClass = terminalClass;
  }

  /**
   * Sets the wallClass.
   * 
   * @param wallClass The wallClass to set
   */
  public void setWallClass(Class wallClass) {
    this.wallClass = wallClass;
  }
	/**
	 * @return
	 */
	public Random getRandomizer() {
		return randomizer;
	}

	/**
	 * @param random
	 */
	public void setRandomizer(Random random) {
		randomizer = random;
	}

}