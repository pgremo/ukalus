package ukalus.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class MarkovChain<E> implements Iterable<E> {

  private Map<E, Bag<E>> items = new HashMap<>();
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
    items.computeIfAbsent(current, k -> new HashBag<>()).add(next);
  }

  @NotNull
  public Iterator<E> iterator() {
    return new MarkovChainIterator<>(items, random);
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
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
}