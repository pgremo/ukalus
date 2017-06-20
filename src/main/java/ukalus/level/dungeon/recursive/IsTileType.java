package ukalus.level.dungeon.recursive;

import ukalus.Tile;

import java.util.function.Function;
import java.util.function.Predicate;

public class IsTileType implements Predicate<Tile> {

  private Class tileTypeClass;

  public IsTileType(Class tileTypeClass) {
    this.tileTypeClass = tileTypeClass;
  }

  public boolean test(Tile value) {
    return value != null && tileTypeClass.equals(value.getTileType()
      .getClass());
  }
}