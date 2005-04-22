package ironfist.math;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class Vector2DTest extends TestCase {

  public void testRotate() {
    Vector2D direction = Vector2D.get(0, -1);
    assertEquals(Vector2D.get(0, 0), Vector2D.get(0, 0)
      .rotate(direction));
    assertEquals(Vector2D.get(-1, 0), Vector2D.get(0, -1)
      .rotate(direction));
    assertEquals(Vector2D.get(-2, 0), Vector2D.get(0, -2)
      .rotate(direction));
    assertEquals(Vector2D.get(-3, 0), Vector2D.get(0, -3)
      .rotate(direction));
  }

  public void testFindInHashMap() {
    Map<Vector2D, Object> map = new HashMap<Vector2D, Object>();
    map.put(Vector2D.get(1, 0), null);
    assertTrue(map.containsKey(Vector2D.get(1, 0)));
  }

  public void testAdd() {
    Vector2D result = Vector2D.get(1, 0);
    Vector2D angle = Vector2D.get(1, 1);
    result = result.add(angle);
    assertEquals(Vector2D.get(2, 1), result);
  }

  public void testSubtract() {
    Vector2D result = Vector2D.get(1, 0);
    Vector2D angle = Vector2D.get(1, 1);
    result = result.subtract(angle);
    assertEquals(Vector2D.get(0, -1), result);
  }

}
