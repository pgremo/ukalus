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
    assertEquals(new Vector(0, -1), new Vector(1, 0).rotate(90));
  }

}