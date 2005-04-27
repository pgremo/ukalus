package ironfist.level.dungeon.recursive;

import ironfist.Tile;
import ironfist.TileType;
import ironfist.math.Vector2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RectangleRoomFactory {

  private Random randomizer;
  private Class<? extends TileType> floorClass;
  private Class<? extends TileType> wallClass;
  private Class<? extends TileType> terminalClass;
  private int maxRoomHeight;
  private int maxRoomWidth;
  private int minRoomHeight;
  private int minRoomWidth;

  public List<Tile> create() {
    List<Tile> list = new LinkedList<Tile>();
    int height = randomizer.nextInt(maxRoomHeight - minRoomHeight + 1)
        + minRoomHeight - 1;

    int width = randomizer.nextInt(maxRoomWidth - minRoomWidth + 1)
        + minRoomWidth - 1;

    try {
      for (int i = 1; i < height; i++) {
        for (int j = 1; j < width; j++) {
          list.add(new Tile(Vector2D.get(i, j), floorClass.newInstance()));
        }
      }

      boolean flip1 = randomizer.nextBoolean();
      boolean flip2 = randomizer.nextBoolean();
      for (int i = 1; i < height; i++) {
        list.add(new Tile(Vector2D.get(i, 0),
            flip1 ? terminalClass.newInstance() : wallClass.newInstance()));
        list.add(new Tile(Vector2D.get(i, width),
            flip2 ? terminalClass.newInstance() : wallClass.newInstance()));
        flip1 = !flip1;
        flip2 = !flip2;
      }

      flip1 = randomizer.nextBoolean();
      flip2 = randomizer.nextBoolean();
      for (int i = 1; i < width; i++) {
        list.add(new Tile(Vector2D.get(0, i),
            flip1 ? terminalClass.newInstance() : wallClass.newInstance()));
        list.add(new Tile(Vector2D.get(height, i),
            flip2 ? terminalClass.newInstance() : wallClass.newInstance()));
        flip1 = !flip1;
        flip2 = !flip2;
      }

      list.add(new Tile(Vector2D.get(0, 0), wallClass.newInstance()));
      list.add(new Tile(Vector2D.get(0, width), wallClass.newInstance()));
      list.add(new Tile(Vector2D.get(height, 0), wallClass.newInstance()));
      list.add(new Tile(Vector2D.get(height, width), wallClass.newInstance()));

    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
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
   * @param maxRoomHeight
   *          The maxRoomHeight to set
   */
  public void setMaxRoomHeight(int maxRoomHeight) {
    this.maxRoomHeight = maxRoomHeight;
  }

  /**
   * Sets the maxRoomWidth.
   * 
   * @param maxRoomWidth
   *          The maxRoomWidth to set
   */
  public void setMaxRoomWidth(int maxRoomWidth) {
    this.maxRoomWidth = maxRoomWidth;
  }

  /**
   * Sets the minRoomHeight.
   * 
   * @param minRoomHeight
   *          The minRoomHeight to set
   */
  public void setMinRoomHeight(int minRoomHeight) {
    this.minRoomHeight = minRoomHeight;
  }

  /**
   * Sets the minRoomWidth.
   * 
   * @param minRoomWidth
   *          The minRoomWidth to set
   */
  public void setMinRoomWidth(int minRoomWidth) {
    this.minRoomWidth = minRoomWidth;
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
   * Sets the floorClass.
   * 
   * @param floorClass
   *          The floorClass to set
   */
  public void setFloorClass(Class<? extends TileType> floorClass) {
    this.floorClass = floorClass;
  }

  /**
   * Sets the wallClass.
   * 
   * @param wallClass
   *          The wallClass to set
   */
  public void setWallClass(Class<? extends TileType> wallClass) {
    this.wallClass = wallClass;
  }

  public void setTerminalClass(Class<? extends TileType> terminalClass) {
    this.terminalClass = terminalClass;
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