package ukalus.level.dungeon.recursive;

import ukalus.Tile;
import ukalus.TileType;
import ukalus.math.Vector2D;

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

  public RectangleRoomFactory(Random randomizer,
      Class<? extends TileType> floorClass,
      Class<? extends TileType> wallClass,
      Class<? extends TileType> terminalClass, int maxRoomHeight,
      int maxRoomWidth, int minRoomHeight, int minRoomWidth) {
    this.randomizer = randomizer;
    this.floorClass = floorClass;
    this.wallClass = wallClass;
    this.terminalClass = terminalClass;
    this.maxRoomHeight = maxRoomHeight;
    this.maxRoomWidth = maxRoomWidth;
    this.minRoomHeight = minRoomHeight;
    this.minRoomWidth = minRoomWidth;
  }

  public List<Tile> create() {
    List<Tile> list = new LinkedList<Tile>();
    int height = randomizer.nextInt(maxRoomHeight - minRoomHeight + 1)
        + minRoomHeight - 1;

    int width = randomizer.nextInt(maxRoomWidth - minRoomWidth + 1)
        + minRoomWidth - 1;

    try {
      for (int i = 1; i < height; i++) {
        for (int j = 1; j < width; j++) {
          list.add(new Tile(new Vector2D(i, j), floorClass.newInstance()));
        }
      }

      boolean flip1 = randomizer.nextBoolean();
      boolean flip2 = randomizer.nextBoolean();
      for (int i = 1; i < height; i++) {
        list.add(new Tile(new Vector2D(i, 0), flip1
            ? terminalClass.newInstance()
            : wallClass.newInstance()));
        list.add(new Tile(new Vector2D(i, width), flip2
            ? terminalClass.newInstance()
            : wallClass.newInstance()));
        flip1 = !flip1;
        flip2 = !flip2;
      }

      flip1 = randomizer.nextBoolean();
      flip2 = randomizer.nextBoolean();
      for (int i = 1; i < width; i++) {
        list.add(new Tile(new Vector2D(0, i), flip1
            ? terminalClass.newInstance()
            : wallClass.newInstance()));
        list.add(new Tile(new Vector2D(height, i), flip2
            ? terminalClass.newInstance()
            : wallClass.newInstance()));
        flip1 = !flip1;
        flip2 = !flip2;
      }

      list.add(new Tile(new Vector2D(0, 0), wallClass.newInstance()));
      list.add(new Tile(new Vector2D(0, width), wallClass.newInstance()));
      list.add(new Tile(new Vector2D(height, 0), wallClass.newInstance()));
      list.add(new Tile(new Vector2D(height, width), wallClass.newInstance()));

    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    }

    return list;
  }

}