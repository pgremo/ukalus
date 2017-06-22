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
    Vector2D direction = Vector2D.Companion.get(0, -1);
    assertEquals(Vector2D.Companion.get(0, 0), Vector2D.Companion.get(0, 0)
      .rotate(direction));
    assertEquals(Vector2D.Companion.get(-1, 0), Vector2D.Companion.get(0, -1)
      .rotate(direction));
    assertEquals(Vector2D.Companion.get(-2, 0), Vector2D.Companion.get(0, -2)
      .rotate(direction));
    assertEquals(Vector2D.Companion.get(-3, 0), Vector2D.Companion.get(0, -3)
      .rotate(direction));
  }

  public void testFindInHashMap() {
    Map<Vector2D, Object> map = new HashMap<>();
    map.put(Vector2D.Companion.get(1, 0), null);
    assertTrue(map.containsKey(Vector2D.Companion.get(1, 0)));
  }

  public void testAdd() {
    Vector2D result = Vector2D.Companion.get(1, 0);
    Vector2D angle = Vector2D.Companion.get(1, 1);
    result = result.plus(angle);
    assertEquals(Vector2D.Companion.get(2, 1), result);
  }

  public void testSubtract() {
    Vector2D result = Vector2D.Companion.get(1, 0);
    Vector2D angle = Vector2D.Companion.get(1, 1);
    result = result.minus(angle);
    assertEquals(Vector2D.Companion.get(0, -1), result);
  }

  public void testSerialization() throws IOException, ClassNotFoundException {
    Vector2D expected = Vector2D.Companion.get(1, 1);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oout = new ObjectOutputStream(out);
    oout.writeObject(expected);
    ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
    Vector2D actual = (Vector2D) oin.readObject();
    assertEquals(expected, actual);
    assertEquals(expected, actual);
  }

  public void testGetDirection() {
    Vector2D start = Vector2D.Companion.get(1, 0);
    Vector2D end = Vector2D.Companion.get(3, 0);
    assertEquals(Vector2D.Companion.get(1, 0), end.minus(start).normal());
  }

}
