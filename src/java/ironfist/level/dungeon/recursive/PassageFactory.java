package ironfist.level.dungeon.recursive;

import ironfist.Tile;
import ironfist.TileType;
import ironfist.math.Vector2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PassageFactory {

  private Random randomizer;
  private int baseLength;
  private Class<? extends TileType> floorClass;
  private Class<? extends TileType> wallClass;
  private Class<? extends TileType> terminalClass;

  public List<Tile> create() {
    List<Tile> list = new LinkedList<Tile>();
    int length = 3 + randomizer.nextInt(baseLength);

    try {
      int distance = 1;
      list.add(new Tile(Vector2D.get(distance, -1), wallClass.newInstance()));
      list.add(new Tile(Vector2D.get(distance, 0), wallClass.newInstance()));
      list.add(new Tile(Vector2D.get(distance, 1), wallClass.newInstance()));

      while (distance < length - 1) {
        distance++;
        list.add(new Tile(Vector2D.get(distance, -1), wallClass.newInstance()));
        list.add(new Tile(Vector2D.get(distance, 0), floorClass.newInstance()));
        list.add(new Tile(Vector2D.get(distance, 1), wallClass.newInstance()));
      }

      distance++;
      list.add(new Tile(Vector2D.get(distance, -1), terminalClass.newInstance()));
      list.add(new Tile(Vector2D.get(distance, 0), floorClass.newInstance()));
      list.add(new Tile(Vector2D.get(distance, 1), terminalClass.newInstance()));

      distance++;
      list.add(new Tile(Vector2D.get(distance, -1), wallClass.newInstance()));
      list.add(new Tile(Vector2D.get(distance, 0), terminalClass.newInstance()));
      list.add(new Tile(Vector2D.get(distance, 1), wallClass.newInstance()));
    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    }

    return list;
  }

  public int getBaseLength() {
    return baseLength;
  }

  public void setBaseLength(int baseLength) {
    this.baseLength = baseLength;
  }

  public Class getFloorClass() {
    return floorClass;
  }

  public Class getTerminalClass() {
    return terminalClass;
  }

  public Class getWallClass() {
    return wallClass;
  }

  public void setFloorClass(Class<? extends TileType> floorClass) {
    this.floorClass = floorClass;
  }

  public void setTerminalClass(Class<? extends TileType> terminalClass) {
    this.terminalClass = terminalClass;
  }

  public void setWallClass(Class<? extends TileType> wallClass) {
    this.wallClass = wallClass;
  }

  public Random getRandomizer() {
    return randomizer;
  }

  public void setRandomizer(Random random) {
    randomizer = random;
  }

}