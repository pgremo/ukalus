package ironfist.geometry.test;

import ironfist.geometry.Vector;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class VectorTest extends TestCase {
  /**
   * Constructor for CoordinateTest.
   *
   * @param arg0
   */
  public VectorTest(String arg0) {
    super(arg0);
  }

  /**
   * DOCUMENT ME!
   */
  public void testFindInHashMap() {
    Map map = new HashMap();
    map.put(new Vector(1, 0), null);
    assertTrue(map.containsKey(new Vector(1, 0)));
  }

  /**
   * DOCUMENT ME!
   */
  public void testAdd() {
    Vector result = new Vector(1, 0);
    Vector angle = new Vector(1, 1);
    result = result.add(angle);
    assertEquals(new Vector(2, 1), result);
  }

  /**
   * DOCUMENT ME!
   */
  public void testSubtract() {
    Vector result = new Vector(1, 0);
    Vector angle = new Vector(1, 1);
    result = result.subtract(angle);
    assertEquals(new Vector(0, -1), result);
  }

  /**
   * DOCUMENT ME!
   */
//  public void testRotate() {
//    Vector start = new Vector(2, 1);
//    assertEquals(new Vector(1, 1).normal(),
//      start.rotate(new Vector(2, 1)).normal());
//  }
}
