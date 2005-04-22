package ironfist.math;

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

}
