package ukalus.level.dungeon.recursive;

import ukalus.Level;
import ukalus.Tile;
import ukalus.math.Vector2D;

import java.util.function.Consumer;
import java.util.function.Function;

public class Place implements Consumer<Tile> {

  private Level level;
  private Vector2D coordinate;

  public Place(Level level, Vector2D coordinate) {
    this.level = level;
    this.coordinate = coordinate;
  }

  @Override
  public void accept(Tile current) {
    level.set(current.getLocation()
      .plus(coordinate), current.getTileType());
  }
}
