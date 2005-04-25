package ironfist.math;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class Vector2D implements Serializable {

  private static final long serialVersionUID = 3256441421631207224L;

  private static int RANGE = 200;

  public static int MIN_X = -RANGE;
  public static int MAX_X = RANGE;
  public static int MIN_Y = -RANGE;
  public static int MAX_Y = RANGE;

  private static final Vector2D[][] cache = new Vector2D[RANGE * 2][RANGE * 2];

  public static Vector2D get(int x, int y) {
    int tx = x + RANGE;
    int ty = y + RANGE;
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

  public Vector2D subtract(Vector2D value) {
    return get(x - value.x, y - value.y);
  }

  public Vector2D multiply(double value) {
    return get((int) (x * value), (int) (y * value));
  }

  public double distance(Vector2D destination) {
    return subtract(destination).magnitude();
  }

  public double magnitude() {
    return Math.sqrt((x * x) + (y * y));
  }

  public Vector2D rotate(Vector2D vector) {
    return get((x * vector.x) - (y * vector.y), (x * vector.y) + (y * vector.x));
  }

  public Vector2D orthoganal() {
    return get(y, -x);
  }

  public Object clone() {
    return get(x, y);
  }

  public String toString() {
    return "(x=" + x + ",y=" + y + ")";
  }

  Object writeReplace() throws ObjectStreamException {
    return new SerializedForm(x, y);
  }

  private static class SerializedForm implements Serializable {

    private static final long serialVersionUID = 3257848779250939185L;

    private int x;
    private int y;

    public SerializedForm(int x, int y) {
      this.x = x;
      this.y = y;
    }

    Object readResolve() throws ObjectStreamException {
      return get(x, y);
    }

  }

}
