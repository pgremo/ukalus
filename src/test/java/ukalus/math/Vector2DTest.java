package ukalus.math;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class Vector2DTest extends TestCase {

  public void testRotate() {
    Vector2D direction = new Vector2D(0, -1);
    assertEquals(new Vector2D(0, 0), new Vector2D(0, 0)
      .rotate(direction));
    assertEquals(new Vector2D(-1, 0), new Vector2D(0, -1)
      .rotate(direction));
    assertEquals(new Vector2D(-2, 0), new Vector2D(0, -2)
      .rotate(direction));
    assertEquals(new Vector2D(-3, 0), new Vector2D(0, -3)
      .rotate(direction));
  }

  public void testFindInHashMap() {
    Map<Vector2D, Object> map = new HashMap<>();
    map.put(new Vector2D(1, 0), null);
    assertTrue(map.containsKey(new Vector2D(1, 0)));
  }

  public void testAdd() {
    Vector2D result = new Vector2D(1, 0);
    Vector2D angle = new Vector2D(1, 1);
    result = result.plus(angle);
    assertEquals(new Vector2D(2, 1), result);
  }

  public void testSubtract() {
    Vector2D result = new Vector2D(1, 0);
    Vector2D angle = new Vector2D(1, 1);
    result = result.minus(angle);
    assertEquals(new Vector2D(0, -1), result);
  }

  public void testSerialization() throws IOException, ClassNotFoundException {
    Vector2D expected = new Vector2D(1, 1);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oout = new ObjectOutputStream(out);
    oout.writeObject(expected);
    ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
    Vector2D actual = (Vector2D) oin.readObject();
    assertEquals(expected, actual);
    assertEquals(expected, actual);
  }

  public void testGetDirection() {
    Vector2D start = new Vector2D(1, 0);
    Vector2D end = new Vector2D(3, 0);
    assertEquals(new Vector2D(1, 0), end.minus(start).normal());
  }

}
