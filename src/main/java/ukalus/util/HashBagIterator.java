package ukalus.util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class HashBagIterator<E> implements Iterator<E> {

  private final HashBag<E> bag;
  private Iterator<Map.Entry<E, AtomicInteger>> entryIterator;
  private Map.Entry<E, AtomicInteger> current;
  private int count;
  private int modifications;
  private boolean canRemove;

  HashBagIterator(HashBag<E> bag, Iterator<Map.Entry<E, AtomicInteger>> iterator) {
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
      count = current.getValue().get();
    }
    canRemove = true;
    count--;
    return current.getKey();
  }

  public void remove() {
    if (modifications != this.bag.modifications) {
      throw new ConcurrentModificationException();
    }
    if (!canRemove) {
      throw new IllegalStateException();
    }
    AtomicInteger counter = current.getValue();
    if (counter.get() > 0) {
      counter.decrementAndGet();
      this.bag.size--;
    } else {
      entryIterator.remove();
    }
    canRemove = false;
  }
}