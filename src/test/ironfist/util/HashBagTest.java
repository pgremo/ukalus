/*
 * Created on Aug 19, 2004
 *
 */
package ironfist.util;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class HashBagTest extends TestCase {

  public void testSize() {
    Bag<Object> bag = new HashBag<Object>();
    Object object = new Object();
    assertEquals(0, bag.size());
    assertTrue(bag.add(object));
    assertEquals(1, bag.size());
  }

  public void testRemove() {
    Bag<Object> bag = new HashBag<Object>();
    Object object1 = new Object();
    Object object2 = new Object();
    bag.add(object1);
    bag.add(object2);
    bag.add(object2);
    assertEquals(3, bag.size());
    assertEquals(1, bag.occurence(object1));
    assertEquals(2, bag.occurence(object2));
    bag.remove(object2);
    assertEquals(2, bag.size());
    assertEquals(1, bag.occurence(object2));
    assertTrue(!bag.remove(new Object()));

  }

  public void testOccurence() {
    Bag<Object> bag = new HashBag<Object>();
    Object object = new Object();
    assertEquals(0, bag.size());
    assertTrue(bag.add(object));
    assertEquals(1, bag.size());
    assertEquals(1, bag.occurence(object));
  }
  
  public void testIteratorSingle(){
    Bag<Object> bag = new HashBag<Object>();
    Object object = new Object();
    assertEquals(0, bag.size());
    assertTrue(bag.add(object));
    assertEquals(1, bag.size());
    Iterator iterator = bag.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(object, iterator.next());
    assertTrue(!iterator.hasNext());
  }

  public void testIteratorMultiple(){
    Bag<Object> bag = new HashBag<Object>();
    Object o1 = new Object();
    Object o2 = new Object();
    Object o3 = o1;
    assertEquals(0, bag.size());
    assertTrue(bag.add(o1));
    assertTrue(bag.add(o2));
    assertTrue(bag.add(o3));
    assertEquals(3, bag.size());
    Iterator iterator = bag.iterator();
    assertTrue(iterator.hasNext());
    assertNotNull(iterator.next());
    assertTrue(iterator.hasNext());
    assertNotNull(iterator.next());
    assertTrue(iterator.hasNext());
    assertNotNull(iterator.next());
    assertTrue(!iterator.hasNext());
  }
}