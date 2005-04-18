package ironfist.math;

public class Vector2D {

  private static final Vector2D[][] cache = new Vector2D[48][160];

  public static Vector2D get(int x, int y) {
    int tx = x + 24;
    int ty = y + 80;
    Vector2D result = cache[tx][ty];
    if (result == null) {
      result = new Vector2D(x, y);
      cache[tx][ty] = result;
    }
    return result;
  }

  private int x;
  private int y;

  private Vector2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Vector2D add(Vector2D value) {
    return get(x + value.x, y + value.y);
  }

  public Object clone() {
    return get(x, y);
  }

  public String toString() {
    return "(x=" + x + ",y=" + y + ")";
  }

}
