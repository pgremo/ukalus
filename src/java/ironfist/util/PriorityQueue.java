/*
 * Created on Feb 11, 2005
 *  
 */
package ironfist.util;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public class PriorityQueue extends AbstractCollection implements Serializable {

  private static final long serialVersionUID = -7720805057305804111L;
  private static final int DEFAULT_INITIAL_CAPACITY = 11;
  private static final Comparator DEFAULT_COMPARATOR = new ComparableComparator();

  private transient Object[] queue;
  private int size = 0;
  private Comparator comparator;
  private transient int modCount = 0;

  public PriorityQueue() {
    this(DEFAULT_INITIAL_CAPACITY, DEFAULT_COMPARATOR);
  }

  public PriorityQueue(int initialCapacity) {
    this(initialCapacity, DEFAULT_COMPARATOR);
  }

  public PriorityQueue(Comparator comparator) {
    this(DEFAULT_INITIAL_CAPACITY, comparator);
  }

  public PriorityQueue(int initialCapacity, Comparator comparator) {
    if (initialCapacity < 1)
      throw new IllegalArgumentException();
    this.queue = new Object[initialCapacity + 1];
    this.comparator = comparator;
  }

  private void initializeArray(Collection c) {
    int initialCapacity = (int) Math.min((c.size() * 110L) / 100,
      Integer.MAX_VALUE - 1);
    if (initialCapacity < 1) {
      initialCapacity = 1;
    }

    this.queue = new Object[initialCapacity + 1];
  }

  private void fillFromSorted(Collection c) {
    for (Iterator i = c.iterator(); i.hasNext();) {
      queue[++size] = i.next();
    }
  }

  private void fillFromUnsorted(Collection c) {
    for (Iterator i = c.iterator(); i.hasNext();) {
      queue[++size] = i.next();
    }
    heapify();
  }

  public PriorityQueue(Collection c) {
    initializeArray(c);
    if (c instanceof SortedSet) {
      SortedSet s = (SortedSet) c;
      comparator = s.comparator();
      fillFromSorted(s);
    } else if (c instanceof PriorityQueue) {
      PriorityQueue s = (PriorityQueue) c;
      comparator = s.comparator();
      fillFromSorted(s);
    } else {
      comparator = null;
      fillFromUnsorted(c);
    }
  }

  public PriorityQueue(PriorityQueue c) {
    initializeArray(c);
    comparator = c.comparator();
    fillFromSorted(c);
  }

  public PriorityQueue(SortedSet c) {
    initializeArray(c);
    comparator = c.comparator();
    fillFromSorted(c);
  }

  private void grow(int index) {
    int newlen = queue.length;
    if (index < newlen) {
      return;
    }
    if (index == Integer.MAX_VALUE) {
      throw new OutOfMemoryError();
    }
    while (newlen <= index) {
      if (newlen >= Integer.MAX_VALUE / 2) {
        newlen = Integer.MAX_VALUE;
      } else {
        newlen <<= 2;
      }
    }
    Object[] newQueue = new Object[newlen];
    System.arraycopy(queue, 0, newQueue, 0, queue.length);
    queue = newQueue;
  }

  public Object getFirst() {
    Object result = null;
    if (size > 0) {
      result = queue[1];
    }
    return result;
  }

  public boolean add(Object o) {
    if (o == null) {
      throw new NullPointerException();
    }
    modCount++;
    size++;

    // Grow backing store if necessary
    if (size >= queue.length) {
      grow(size);
    }

    queue[size] = o;
    fixUp(size);
    return true;
  }

  public boolean remove(Object o) {
    if (o == null) {
      return false;
    }

    boolean found = false;
    int i = 1;
    while (i <= size && !found) {
      found = comparator.compare(queue[i++], o) == 0;
    }
    if (found) {
      removeAt(i);
    }
    return found;
  }

  public Iterator iterator() {
    return new Itr();
  }

  private class Itr implements Iterator {

    private int cursor = 1;
    private int lastRet = 0;
    private int expectedModCount = modCount;
    private ArrayList forgetMeNot = null;

    private Object lastRetElt = null;

    public boolean hasNext() {
      return cursor <= size || forgetMeNot != null;
    }

    public Object next() {
      checkForComodification();
      Object result;
      if (cursor <= size) {
        result = queue[cursor];
        lastRet = cursor++;
      } else if (forgetMeNot == null)
        throw new NoSuchElementException();
      else {
        int remaining = forgetMeNot.size();
        result = forgetMeNot.remove(remaining - 1);
        if (remaining == 1)
          forgetMeNot = null;
        lastRet = 0;
        lastRetElt = result;
      }
      return result;
    }

    public void remove() {
      checkForComodification();

      if (lastRet != 0) {
        Object moved = PriorityQueue.this.removeAt(lastRet);
        lastRet = 0;
        if (moved == null) {
          cursor--;
        } else {
          if (forgetMeNot == null)
            forgetMeNot = new ArrayList();
          forgetMeNot.add(moved);
        }
      } else if (lastRetElt != null) {
        PriorityQueue.this.remove(lastRetElt);
        lastRetElt = null;
      } else {
        throw new IllegalStateException();
      }

      expectedModCount = modCount;
    }

    final void checkForComodification() {
      if (modCount != expectedModCount)
        throw new ConcurrentModificationException();
    }
  }

  public int size() {
    return size;
  }

  public void clear() {
    modCount++;

    while (size >= 0) {
      queue[size--] = null;
    }
  }

  public Object removeFirst() {
    if (size == 0) {
      return null;
    }
    modCount++;

    Object result = queue[1];
    queue[1] = queue[size];
    queue[size--] = null;
    if (size > 1) {
      fixDown(1);
    }

    return result;
  }

  private Object removeAt(int i) {
    modCount++;

    Object moved = queue[size];
    queue[i] = moved;
    queue[size--] = null; // Drop extra ref to prevent memory leak
    if (i <= size) {
      fixDown(i);
      if (queue[i] == moved) {
        fixUp(i);
        if (queue[i] != moved)
          return moved;
      }
    }
    return null;
  }

  private void fixUp(int k) {
    while (k > 1) {
      int j = k >>> 1;
      if (comparator.compare(queue[j], queue[k]) <= 0)
        break;
      Object tmp = queue[j];
      queue[j] = queue[k];
      queue[k] = tmp;
      k = j;
    }
  }

  private void fixDown(int k) {
    int j;
    while ((j = k << 1) <= size && (j > 0)) {
      if (j < size && comparator.compare(queue[j], queue[j + 1]) > 0)
        j++; // j indexes smallest kid
      if (comparator.compare(queue[k], queue[j]) <= 0)
        break;
      Object tmp = queue[j];
      queue[j] = queue[k];
      queue[k] = tmp;
      k = j;
    }
  }

  private void heapify() {
    for (int i = size / 2; i >= 1; i--)
      fixDown(i);
  }

  public Comparator comparator() {
    return comparator;
  }

  private void writeObject(java.io.ObjectOutputStream s)
      throws java.io.IOException {
    // Write out element count, and any hidden stuff
    s.defaultWriteObject();

    // Write out array length
    s.writeInt(queue.length);

    // Write out all elements in the proper order.
    for (int i = 1; i <= size; i++)
      s.writeObject(queue[i]);
  }

  private void readObject(java.io.ObjectInputStream s)
      throws java.io.IOException, ClassNotFoundException {
    // Read in size, and any hidden stuff
    s.defaultReadObject();

    // Read in array length and allocate array
    int arrayLength = s.readInt();
    queue = new Object[arrayLength];

    // Read in all elements in the proper order.
    for (int i = 1; i <= size; i++)
      queue[i] = s.readObject();
  }

}