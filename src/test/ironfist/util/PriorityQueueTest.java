/*
 * Created on Feb 11, 2005
 *
  */
package ironfist.util;

import junit.framework.TestCase;


/**
 * @author gremopm
 *
 */
public class PriorityQueueTest extends TestCase {
  
  public void testAddDuplicates(){
    PriorityQueue collection = new PriorityQueue();
    collection.add(new Integer(2));
    collection.add(new Integer(2));
    collection.add(new Integer(1));
    assertEquals(3, collection.size());
    assertEquals(new Integer(1), collection.getFirst());
    assertTrue(collection.contains(new Integer(2)));
  }

}
