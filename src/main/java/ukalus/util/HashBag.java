package ukalus.util;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.unmodifiableSet;


public class HashBag<E> extends AbstractCollection<E> implements Bag<E> {

  private Map<E, AtomicInteger> items = new HashMap<>();
  int size;
  int modifications;

  @NotNull
  public Iterator<E> iterator() {
    return new HashBagIterator<>(this, items.entrySet().iterator());
  }

  public boolean add(E value) {
    AtomicInteger counter = items.computeIfAbsent(value, k -> new AtomicInteger());
    counter.incrementAndGet();
    size++;
    return true;
  }

  public int occurrence(E value) {
    AtomicInteger counter = items.get(value);
    return counter == null ? 0 : counter.get();
  }

  public Iterator<Map.Entry<E, AtomicInteger>> occurrenceIterator() {
    return unmodifiableSet(items.entrySet()).iterator();
  }

  public int size() {
    return size;
  }
}