package ironfist.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public final class Block {

  private Block() {

  }

  public static <T> T detect(Collection<T> collection, Constraint constraint) {
    T result = null;
    Iterator<T> iterator = collection.iterator();
    while (iterator.hasNext() && result == null) {
      T current = iterator.next();
      if (constraint.allow(current)) {
        result = current;
      }
    }
    return result;
  }

  public static <T> Collection<T> collect(Collection<T> collection,
      Constraint constraint) {
    Collection<T> result = new LinkedList<T>();
    Iterator<T> iterator = collection.iterator();
    while (iterator.hasNext()) {
      T current = iterator.next();
      if (constraint.allow(current)) {
        result.add(current);
      }
    }
    return result;
  }

}
