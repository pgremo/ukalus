package ironfist.util;

import java.util.AbstractCollection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class HashBag<E> extends AbstractCollection<E> implements Bag<E> {

  private Map<E, Counter> items = new HashMap<E, Counter>();
  int size;
  int modifications;

  public Iterator<E> iterator() {
    return new HashBagIterator<E>(this, items.entrySet()
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
}