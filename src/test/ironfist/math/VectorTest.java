/*
 * Created on Feb 21, 2005
 *
 */
package ironfist.math;

import junit.framework.TestCase;

/**
 * @author pmgremo
 *  
 */
public class VectorTest extends TestCase {

  public void testRotate() {
    Vector actual = new Vector(1, 0).rotate(90);
    assertEquals(0, (int)actual.getX());
    assertEquals(-1, (int)actual.getY());
  }

}