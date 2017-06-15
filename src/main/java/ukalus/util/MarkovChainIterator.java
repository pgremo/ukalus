package ukalus.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

class MarkovChainIterator<E> implements Iterator<E> {

  private Map<E, Bag<E>> items;
  private Random random;
  private E next;

  public MarkovChainIterator(Map<E, Bag<E>> items, Random random) {
    this.items = items;
    this.random = random;
    setNext();
  }

  public boolean hasNext() {
    return next != null;
  }

  public E next() {
    E result = next;
    setNext();
    return result;
  }

  private void setNext() {
    Map.Entry<E, Counter> result = null;
    Bag<E> chain = items.get(next);
    if (chain != null) {
      int limit = (int) Math.ceil(chain.size() * random.nextDouble());
      Iterator<Map.Entry<E, Counter>> iterator = chain.occurenceIterator();
      while (limit > 0) {
        result = iterator.next();
        limit -= result.getValue()
          .getCount();
      }
    }
    next = result == null ? null : result.getKey();
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

}