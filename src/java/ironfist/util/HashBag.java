/*
 * Created on Aug 19, 2004
 *
 */
package ironfist.util;

import java.util.AbstractCollection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gremopm
 *  
 */
public class HashBag extends AbstractCollection implements Bag {

  private Map items = new OrderedHashMap();
  private int size;
  private int modifications;

  public Iterator iterator() {
    return new HashBagIterator(items.entrySet()
      .iterator());
  }

  public boolean add(Object value) {
    Counter counter = (Counter) items.get(value);
    if (counter == null) {
      counter = new Counter();
      items.put(value, counter);
    }
    counter.increment();
    size++;
    return true;
  }

  public int occurence(Object value) {
    Counter counter = (Counter) items.get(value);
    return counter == null ? 0 : counter.getCount();
  }

  public Iterator occurenceIterator() {
    return Collections.unmodifiableSet(items.entrySet())
      .iterator();
  }

  public int size() {
    return size;
  }

  class HashBagIterator implements Iterator {

    private Iterator entryIterator;
    private Map.Entry current;
    private int count;
    private final int modifications = HashBag.this.modifications;
    private boolean canRemove;

    /**
     * Constructor.
     * 
     * @param parent
     *          the parent bag
     */
    public HashBagIterator(Iterator iterator) {
      this.entryIterator = iterator;
      this.current = null;
    }

    public boolean hasNext() {
      return (count > 0 || entryIterator.hasNext());
    }

    public Object next() {
      if (modifications != HashBag.this.modifications) {
        throw new ConcurrentModificationException();
      }
      if (count == 0) {
        current = (Map.Entry) entryIterator.next();
        count = ((Counter) current.getValue()).getCount();
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
      Counter counter = (Counter) current.getValue();
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