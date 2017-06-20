package ukalus.level.dungeon.recursive;

import ukalus.Tile;
import ukalus.math.Vector2D;

import java.util.function.Consumer;

public class Dimension implements Consumer<Tile> {

  private int height;
  private int width;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public void accept(Tile tile) {
    Vector2D current = tile.getLocation();
    height = Math.max(height, current.getX());
    width = Math.max(width, current.getY());
  }
}
