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
public class MarkovChain<E> {

  private Bag<E> root = new HashBag<E>();
  private Map<E, Bag<E>> items = new HashMap<E, Bag<E>>();

  public void add(E current, E next) {
    Bag<E> chain = items.get(current);
    if (chain == null) {
      chain = new HashBag<E>();
      items.put(current, chain);
      root.add(current);
    }
    chain.add(next);
  }

  public E next(E key, double weight) {
    Bag<E> chain = items.get(key);
    if (chain == null) {
      chain = root;
    }
    Map.Entry<E, Counter> current = null;
    if (chain != null) {
      int level = 0;
      int limit = (int) Math.ceil(chain.size() * weight);
      Iterator<Map.Entry<E, Counter>> iterator = chain.occurenceIterator();
      while (level < limit) {
        current = iterator.next();
        level += current.getValue()
          .getCount();
      }
    }
    return current == null ? null : current.getKey();
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("[ROOT]:")
      .append(" (")
      .append(root.size())
      .append(") ")
      .append(root)
      .append("\n");
    for (Map.Entry<E, Bag<E>> entry : items.entrySet()) {
      Collection list = entry.getValue();
      result.append("[")
        .append(entry.getKey())
        .append("]:")
        .append(" (")
        .append(list.size())
        .append(") ")
        .append(list)
        .append("\n");
    }

    return result.toString();
  }

}