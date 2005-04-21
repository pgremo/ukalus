package ironfist.generator;

import ironfist.Tile;
import ironfist.TileType;
import ironfist.math.Vector2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PassageFactory {

  private Random randomizer;
  private Vector2D direction;
  private int baseLength;
  private Class<? extends TileType> floorClass;
  private Class<? extends TileType> wallClass;
  private Class<? extends TileType> cornerClass;
  private Class<? extends TileType> terminalClass;

  public List<Tile> create() {
    List<Tile> list = new LinkedList<Tile>();
    int length = 3 + randomizer.nextInt(4)
        + ((randomizer.nextInt(5) == 0) ? randomizer.nextInt(5) : 0)
        + baseLength;
    Vector2D right = direction.orthoganal();
    Vector2D left = right.multiply(-1);

    try {
      list.add(new Tile(right.add(direction), cornerClass.newInstance()));
      list.add(new Tile(direction, wallClass.newInstance()));
      list.add(new Tile(left.add(direction), cornerClass.newInstance()));

      for (int index = 2; index < (length - 1); index++) {
        list.add(new Tile(right.add(direction.multiply(index)),
          cornerClass.newInstance()));
        list.add(new Tile(direction.multiply(index), floorClass.newInstance()));
        list.add(new Tile(left.add(direction.multiply(index)),
          wallClass.newInstance()));
      }

      list.add(new Tile(right.add(direction.multiply(length - 1)),
        terminalClass.newInstance()));
      list.add(new Tile(direction.multiply(length - 1),
        floorClass.newInstance()));
      list.add(new Tile(left.add(direction.multiply(length - 1)),
        terminalClass.newInstance()));

      list.add(new Tile(right.add(direction.multiply(length)),
        cornerClass.newInstance()));
      list.add(new Tile(direction.multiply(length), terminalClass.newInstance()));
      list.add(new Tile(left.add(direction.multiply(length)),
        cornerClass.newInstance()));
    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    }

    return list;
  }

  public int getBaseLength() {
    return baseLength;
  }

  public Vector2D getDirection() {
    return direction;
  }

  public void setBaseLength(int baseLength) {
    this.baseLength = baseLength;
  }

  public void setDirection(Vector2D direction) {
    this.direction = direction;
  }

  public Class getCornerClass() {
    return cornerClass;
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

  public void setCornerClass(Class<? extends TileType> cornerClass) {
    this.cornerClass = cornerClass;
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