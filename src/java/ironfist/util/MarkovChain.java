/*
 * Created on Aug 19, 2004
 *
 */
package ironfist.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gremopm
 *  
 */
public class MarkovChain {

  private Map items = new HashMap();

  public void add(Object current, Object next) {
    Collection chain = (Collection) items.get(current);
    if (chain == null) {
      chain = new HashBag();
      items.put(current, chain);
    }
    chain.add(next);
  }

  public Object next(Object key, double weight) {
    Map.Entry current = null;
    Bag chain = (Bag) items.get(key);
    if (chain != null) {
      int level = 0;
      int limit = (int) Math.ceil(chain.size() * weight);
      Iterator iterator = chain.occurenceIterator();
      while (level < limit) {
        current = (Map.Entry) iterator.next();
        level += ((Counter) current.getValue()).getCount();
      }
    }
    Object result = null;
    if (current != null) {
      result = current.getKey();
    }
    return result;
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