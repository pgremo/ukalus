package ironfist.generator;

import ironfist.Tile;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

public class Dimension implements Closure<Tile, Object> {

  private static final long serialVersionUID = 3257571702421532979L;

  private int height;
  private int width;

  public Object apply(Tile tile) {
    Vector2D current = tile.getLocation();
    height = Math.max(height, current.getX());
    width = Math.max(width, current.getY());
    return tile;
  }

  public int getWidth() {
    return width;
  }

  
  public int getHeight() {
    return height;
  }


}
