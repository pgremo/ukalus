/*
 * Created on Feb 21, 2005
 *
 */
package ukalus.math;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author pmgremo
 * 
 */
public class VectorTest extends TestCase {

  public void testRotate() {
    Vector actual = new Vector(1, 0).rotate(90);
    assertEquals(0, (int) actual.getX());
    assertEquals(-1, (int) actual.getY());
  }

  public void testFindInHashMap() {
    Map<Vector, Object> map = new HashMap<Vector, Object>();
    map.put(new Vector(1, 0), null);
    assertTrue(map.containsKey(new Vector(1, 0)));
  }

  public void testAdd() {
    Vector result = new Vector(1, 0);
    Vector angle = new Vector(1, 1);
    result = result.add(angle);
    assertEquals(new Vector(2, 1), result);
  }

  public void testSubtract() {
    Vector result = new Vector(1, 0);
    Vector angle = new Vector(1, 1);
    result = result.subtract(angle);
    assertEquals(new Vector(0, -1), result);
  }

}