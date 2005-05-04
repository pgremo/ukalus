package ironfist.level.dungeon.buck;

import ironfist.math.Vector2D;
import ironfist.util.Closure;

public class Dimension implements Closure<Vector2D, Object> {

  private static final long serialVersionUID = 3257571702421532979L;

  private int height;
  private int width;

  public Object apply(Vector2D current) {
    height = Math.max(height, current.getX());
    width = Math.max(width, current.getY());
    return current;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

}
