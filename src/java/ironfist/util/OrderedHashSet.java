/*
 * Created on Mar 2, 2005
 *
 */
package ironfist.util;

import java.util.AbstractSet;
import java.util.Iterator;

/**
 * @author gremopm
 *  
 */
public class OrderedHashSet extends AbstractSet {

  private static final Object THING = new Object();
  private OrderedHashMap storage = new OrderedHashMap();

  public int size() {
    return storage.size();
  }

  public Iterator iterator() {
    return storage.keySet()
      .iterator();
  }

  public boolean add(Object o) {
    return storage.put(o, THING) == null;
  }

  public boolean contains(Object o) {
    return storage.containsKey(o);
  }

  public boolean remove(Object o) {
    return storage.remove(o) == THING;
  }
}