package ironfist.geometry;

import ironfist.math.Vector;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author pmgremo
 */
public class VectorTest extends TestCase {

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