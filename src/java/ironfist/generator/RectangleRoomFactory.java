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
public class RectangleRoomFactory {
  private Random randomizer;
  private Class floorClass;
  private Class wallClass;
  private Class cornerClass;
  private int maxRoomHeight;
  private int maxRoomWidth;
  private int minRoomHeight;
  private int minRoomWidth;

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public List create() {
    List list = new LinkedList();
    int height = randomizer.nextInt(maxRoomHeight - minRoomHeight + 1) + 
                 minRoomHeight;

    int width = randomizer.nextInt(maxRoomWidth - minRoomWidth + 1) + 
                minRoomWidth;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        TileType type = null;

        try {
          if (((i == 0) && (j == 0)) || ((i == height - 1) && (j == 0)) || 
              ((i == 0) && (j == width - 1)) || 
              ((i == height - 1) && (j == width - 1))) {
            type = (TileType) cornerClass.newInstance();
          } else if ((i == 0) || (j == 0) || (i == height - 1) || 
                     (j == width - 1)) {
            type = (TileType) wallClass.newInstance();
          } else {
            type = (TileType) floorClass.newInstance();
          }
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }

        list.add(new Tile(new Vector(i, j), type));
      }
    }

    return list;
  }

  /**
   * Returns the maxRoomHeight.
   * 
   * @return int
   */
  public int getMaxRoomHeight() {
    return maxRoomHeight;
  }

  /**
   * Returns the maxRoomWidth.
   * 
   * @return int
   */
  public int getMaxRoomWidth() {
    return maxRoomWidth;
  }

  /**
   * Returns the minRoomHeight.
   * 
   * @return int
   */
  public int getMinRoomHeight() {
    return minRoomHeight;
  }

  /**
   * Returns the minRoomWidth.
   * 
   * @return int
   */
  public int getMinRoomWidth() {
    return minRoomWidth;
  }

  /**
   * Sets the maxRoomHeight.
   * 
   * @param maxRoomHeight The maxRoomHeight to set
   */
  public void setMaxRoomHeight(int maxRoomHeight) {
    this.maxRoomHeight = maxRoomHeight;
  }

  /**
   * Sets the maxRoomWidth.
   * 
   * @param maxRoomWidth The maxRoomWidth to set
   */
  public void setMaxRoomWidth(int maxRoomWidth) {
    this.maxRoomWidth = maxRoomWidth;
  }

  /**
   * Sets the minRoomHeight.
   * 
   * @param minRoomHeight The minRoomHeight to set
   */
  public void setMinRoomHeight(int minRoomHeight) {
    this.minRoomHeight = minRoomHeight;
  }

  /**
   * Sets the minRoomWidth.
   * 
   * @param minRoomWidth The minRoomWidth to set
   */
  public void setMinRoomWidth(int minRoomWidth) {
    this.minRoomWidth = minRoomWidth;
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