package ironfist.util;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;

public class ArraySet extends AbstractSet {

  private ArrayList storage = new ArrayList();

  public Iterator iterator() {
    return storage.iterator();
  }

  public int size() {
    return storage.size();
  }

  public boolean add(Object o) {
    boolean found = storage.contains(o);

    if (!found) {
      storage.add(o);
    }

    return !found;
  }
}