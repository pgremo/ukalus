/*
 * Created on Aug 19, 2004
 *
 */
package ironfist.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gremopm
 *  
 */
public class MarkovChain {

  private static final Object ROOT = new Object();
  private Map items = new OrderedHashMap();

  public MarkovChain() {
    items.put(ROOT, new HashBag());
  }

  public void add(Object current, Object next) {
    Collection chain = (Collection) items.get(current);
    if (chain == null) {
      chain = new HashBag();
      items.put(current, chain);
      ((Bag) items.get(ROOT)).add(current);
    }
    chain.add(next);
  }

  public Object next(Object key, double weight) {
    Map.Entry current = null;
    Bag chain = (Bag) items.get(key);
    if (chain == null) {
      chain = (Bag) items.get(ROOT);
    }
    if (chain != null) {
      int level = 0;
      int limit = (int) Math.ceil(chain.size() * weight);
      Iterator iterator = chain.occurenceIterator();
      while (level < limit) {
        current = (Map.Entry) iterator.next();
        level += ((Counter) current.getValue()).getCount();
      }
    }
    return current == null ? null : current.getKey();
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    Iterator keys = items.entrySet()
      .iterator();

    while (keys.hasNext()) {
      Map.Entry entry = (Map.Entry) keys.next();
      Collection list = (Collection) entry.getValue();
      result.append("[" + entry.getKey() + "]:")
        .append(" (" + list.size() + ") ")
        .append(list)
        .append("\n");
    }

    return result.toString();
  }

}