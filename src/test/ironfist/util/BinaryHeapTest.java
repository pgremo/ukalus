/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.util;

import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class BinaryHeapTest extends TestCase {

  public void testHeap() {
    int numItems = 10000;
    BinaryHeap h = new BinaryHeap(numItems);

    for (int i = 37; i != 0; i = (i + 37) % numItems) {
      h.add(new Integer(i));
    }
    for (int i = 1; i < numItems; i++) {
      assertEquals(i, ((Integer) h.removeFirst()).intValue());
    }
    for (int i = 37; i != 0; i = (i + 37) % numItems) {
      h.add(new Integer(i));
    }
    h.add(new Integer(0));
    h.add(new Integer(9999999));
    for (int i = 0; i <= h.size(); i++) {
      assertEquals(i, ((Integer) h.removeFirst()).intValue());
    }
  }
}