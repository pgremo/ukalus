package ironfist.util;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;

public class ArraySet extends AbstractSet {

  private ArrayList storage = new ArrayList();

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Collection#iterator()
   */
  public Iterator iterator() {
    return storage.iterator();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Collection#size()
   */
  public int size() {
    return storage.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Collection#add(java.lang.Object)
   */
  public boolean add(Object o) {
    boolean found = storage.contains(o);

    if (!found) {
      storage.add(o);
    }

    return !found;
  }
}