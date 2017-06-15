package ukalus.util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;

class HashBagIterator<E> implements Iterator<E> {

  private final HashBag<E> bag;
  private Iterator<Map.Entry<E, Counter>> entryIterator;
  private Map.Entry<E, Counter> current;
  private int count;
  private int modifications;
  private boolean canRemove;

  public HashBagIterator(HashBag<E> bag,
      Iterator<Map.Entry<E, Counter>> iterator) {
    this.bag = bag;
    this.modifications = bag.modifications;
    this.entryIterator = iterator;
    this.current = null;
  }

  public boolean hasNext() {
    return count > 0 || entryIterator.hasNext();
  }

  public E next() {
    if (modifications != this.bag.modifications) {
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
    if (modifications != this.bag.modifications) {
      throw new ConcurrentModificationException();
    }
    if (canRemove == false) {
      throw new IllegalStateException();
    }
    Counter counter = current.getValue();
    if (counter.getCount() > 0) {
      counter.decrement();
      this.bag.size--;
    } else {
      entryIterator.remove();
    }
    canRemove = false;
  }
}