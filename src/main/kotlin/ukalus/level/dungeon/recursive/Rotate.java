package ukalus.level.dungeon.recursive;

import ukalus.Tile;
import ukalus.math.Vector2D;

import java.util.function.Consumer;
import java.util.function.Function;

public class Rotate implements Consumer<Tile> {
  private Vector2D direction;

  public Rotate(Vector2D direction) {
    this.direction = direction;
  }

  @Override
  public void accept(Tile current) {
    current.setLocation(current.getLocation()
      .rotate(direction));
  }
}
