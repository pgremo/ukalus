package ironfist.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Loop<E> {

  private Iterable<E> storage;

  public Loop(E[] array) {
    this(Arrays.asList(array));
  }

  public Loop(Iterable<E> list) {
    if (list == null) {
      throw new NullPointerException();
    }
    storage = list;
  }

  public E detect(Closure<E, Boolean> block) {
    E result = null;
    Iterator<E> iterator = storage.iterator();
    while (iterator.hasNext() && result == null) {
      E current = iterator.next();
      if (block.apply(current)) {
        result = current;
      }
    }
    return result;
  }

  public Collection<E> collect(Closure<E, Boolean> block) {
    Collection<E> result = new LinkedList<E>();
    for (E item : storage) {
      if (block.apply(item)) {
        result.add(item);
      }
    }
    return result;
  }

  public void forEach(Closure<E, ?> block) {
    for (E item : storage) {
      block.apply(item);
    }
  }

  public void doWhile(Closure<E, Boolean> block) {
    boolean done = false;
    Iterator<E> iterator = storage.iterator();
    while (iterator.hasNext() && !done) {
      done = block.apply(iterator.next());
    }
  }

}
