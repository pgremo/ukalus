package ironfist.container;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

public class Sequence<E> extends AbstractList<E>
    implements
      RandomAccess,
      Serializable {

  private static final long serialVersionUID = -2764017481108945198L;
  private List<E> storage;

  public Sequence(E[] array) {
    if (array == null) {
      throw new NullPointerException();
    }
    storage = Arrays.asList(array);
  }

  public Sequence(List<E> list) {
    if (list == null) {
      throw new NullPointerException();
    }
    storage = list;
  }

  public int size() {
    return storage.size();
  }

  public Object[] toArray() {
    return storage.toArray();
  }

  public E get(int index) {
    return storage.get(index);
  }

  public E set(int index, E element) {
    return storage.set(index, element);
  }

  public int indexOf(Object o) {
    return storage.indexOf(o);
  }

  public boolean contains(Object o) {
    return indexOf(o) != -1;
  }

  public E detect(Predicate<E> block) {
    E result = null;
    Iterator<E> iterator = storage.iterator();
    while (iterator.hasNext() && result == null) {
      E current = iterator.next();
      if (block.invoke(current)) {
        result = current;
      }
    }
    return result;
  }

  public void forEach(Closure<E> block) {
    for (E item : storage) {
      block.invoke(item);
    }
  }

  public void doWhile(Predicate<E> block) {
    boolean done = false;
    Iterator<E> iterator = storage.iterator();
    while (iterator.hasNext() && !done) {
      done = block.invoke(iterator.next());
    }
  }

}
