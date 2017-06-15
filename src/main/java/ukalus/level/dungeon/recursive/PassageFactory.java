package ukalus.level.dungeon.recursive;

import ukalus.Tile;
import ukalus.TileType;
import ukalus.math.Vector2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PassageFactory {

  private Random randomizer;
  private int baseLength;
  private Class<? extends TileType> floorClass;
  private Class<? extends TileType> wallClass;
  private Class<? extends TileType> terminalClass;

  public PassageFactory(Random randomizer,
      Class<? extends TileType> floorClass,
      Class<? extends TileType> wallClass,
      Class<? extends TileType> terminalClass, int baseLength) {
    this.randomizer = randomizer;
    this.floorClass = floorClass;
    this.wallClass = wallClass;
    this.terminalClass = terminalClass;
    this.baseLength = baseLength;
  }

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

}