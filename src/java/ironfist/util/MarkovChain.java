/*
 * Created on Aug 19, 2004
 *
 */
package ironfist.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * @author gremopm
 * 
 */
public class MarkovChain<E> {

  private Map<E, Bag<E>> items = new HashMap<E, Bag<E>>();
  private Random random;

  public MarkovChain(Random random) {
    this.random = random;
  }

  public void addAll(Iterable<E> items) {
    E key = null;
    for (E item : items) {
      add(key, item);
      key = item;
    }
    add(key, null);
  }

  public void add(E current, E next) {
    Bag<E> chain = items.get(current);
    if (chain == null) {
      chain = new HashBag<E>();
      items.put(current, chain);
    }
    chain.add(next);
  }

  public Iterator<E> iterator() {
    return new ChainIterator<E>(items);
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    for (Map.Entry<E, Bag<E>> entry : items.entrySet()) {
      Collection list = entry.getValue();
      result.append("[")
        .append(entry.getKey())
        .append("]: (")
        .append(list.size())
        .append(") ")
        .append(list)
        .append("\n");
    }
    return result.toString();
  }

  private class ChainIterator<E> implements Iterator<E> {

    private Map<E, Bag<E>> items;
    private E next;

    public ChainIterator(Map<E, Bag<E>> items) {
      this.items = items;
      next = getNext();
    }

    public boolean hasNext() {
      return next != null;
    }

    public E next() {
      E result = next;
      next = getNext();
      return result;
    }

    private E getNext() {
      Map.Entry<E, Counter> result = null;
      Bag<E> chain = items.get(next);
      if (chain != null) {
        int level = 0;
        int limit = (int) Math.ceil(chain.size() * random.nextDouble());
        Iterator<Map.Entry<E, Counter>> iterator = chain.occurenceIterator();
        while (level < limit) {
          result = iterator.next();
          level += result.getValue()
            .getCount();
        }
      }
      return result == null ? null : result.getKey();
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

  }
}