/*
 * Created on Aug 19, 2004
 *
 */
package ironfist.util;

import java.util.AbstractCollection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gremopm
 * 
 */
public class HashBag<E> extends AbstractCollection<E> implements Bag<E> {

  private Map<E, Counter> items = new HashMap<E, Counter>();
  private int size;
  private int modifications;

  public Iterator<E> iterator() {
    return new HashBagIterator<E>(items.entrySet()
      .iterator());
  }

  public boolean add(E value) {
    Counter counter = items.get(value);
    if (counter == null) {
      counter = new Counter();
      items.put(value, counter);
    }
    counter.increment();
    size++;
    return true;
  }

  public int occurence(E value) {
    Counter counter = items.get(value);
    return counter == null ? 0 : counter.getCount();
  }

  public Iterator<Map.Entry<E, Counter>> occurenceIterator() {
    return Collections.unmodifiableSet(items.entrySet())
      .iterator();
  }

  public int size() {
    return size;
  }

  class HashBagIterator<E> implements Iterator<E> {

    private Iterator<Map.Entry<E, Counter>> entryIterator;
    private Map.Entry<E, Counter> current;
    private int count;
    private final int modifications = HashBag.this.modifications;
    private boolean canRemove;

    public HashBagIterator(Iterator<Map.Entry<E, Counter>> iterator) {
      this.entryIterator = iterator;
      this.current = null;
    }

    public boolean hasNext() {
      return count > 0 || entryIterator.hasNext();
    }

    public E next() {
      if (modifications != HashBag.this.modifications) {
        throw new ConcurrentModificationException();
      }
      if (count == 0) {
        current = entryIterator.next();
        count = current.getValue()
          .getCount();
      }
      canRemove = true;
      count--;
      return current.getKey();
    }

    public void remove() {
      if (modifications != HashBag.this.modifications) {
        throw new ConcurrentModificationException();
      }
      if (canRemove == false) {
        throw new IllegalStateException();
      }
      Counter counter = current.getValue();
      if (counter.getCount() > 0) {
        counter.decrement();
        size--;
      } else {
        entryIterator.remove();
      }
      canRemove = false;
    }
  }
}