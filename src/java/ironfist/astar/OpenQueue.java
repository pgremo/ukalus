/*
 * Created on Mar 2, 2005
 *
 */
package ironfist.astar;

import ironfist.util.PriorityQueue;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author gremopm
 *  
 */
public class OpenQueue extends AbstractNodeCollection {

  private PriorityQueue storage;

  public OpenQueue(Comparator comparator) {
    storage = new PriorityQueue(comparator);
  }

  public Object removeFirst() {
    return storage.removeFirst();
  }

  public int size() {
    return storage.size();
  }

  public void clear() {
    storage.clear();
  }

  public boolean isEmpty() {
    return storage.isEmpty();
  }

  public Object[] toArray() {
    return storage.toArray();
  }

  public boolean add(Object o) {
    return storage.add(o);
  }

  public boolean contains(Object o) {
    return storage.contains(o);
  }

  public boolean remove(Object o) {
    return storage.remove(o);
  }

  public boolean addAll(Collection c) {
    return storage.addAll(c);
  }

  public boolean containsAll(Collection c) {
    return storage.containsAll(c);
  }

  public boolean removeAll(Collection c) {
    return storage.removeAll(c);
  }

  public boolean retainAll(Collection c) {
    return storage.retainAll(c);
  }

  public Iterator iterator() {
    return storage.iterator();
  }

  public Object[] toArray(Object[] a) {
    return storage.toArray(a);
  }

}