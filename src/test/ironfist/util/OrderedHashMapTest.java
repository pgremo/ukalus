/*
 * Created on Mar 1, 2005
 *
 */
package ironfist.util;

import java.util.Map;

import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class OrderedHashMapTest extends TestCase {

  public void testPutGet() {
    Map target = new OrderedHashMap();
    Object key = new Object();
    Object value = new Object();
    target.put(key, value);
    assertEquals(value, target.get(key));
    assertEquals(value, target.put(key, new Object()));
  }

  public void testPutGetNullKey() {
    Map target = new OrderedHashMap();
    Object key = null;
    Object value = new Object();
    target.put(key, value);
    assertEquals(value, target.get(key));
    assertEquals(value, target.put(key, new Object()));
  }

  public void testPutGetNullValue() {
    Map target = new OrderedHashMap();
    Object key = new Object();
    Object value = null;
    target.put(key, value);
    assertEquals(value, target.get(key));
    assertEquals(value, target.put(key, new Object()));
  }

  public void testPutGetMultipleEntries() {
    Map target = new OrderedHashMap();
    Object[] keys = new Object[1000];
    Object[] values = new Object[keys.length];
    for (int i = 0; i < keys.length; i++) {
      keys[i] = new Object();
      values[i] = new Object();
      target.put(keys[i], values[i]);
    }
    for (int i = 0; i < keys.length; i++) {
      assertEquals(target.get(keys[i]), values[i]);
    }
  }

}