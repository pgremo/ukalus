/*
 * Created on Feb 10, 2005
 *  
 */
package ironfist.util;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class BinaryHeap {

  private static final int DEFAULT_CAPACITY = 100;
  private static final Comparator DEFAULT_COMPARATOR = new ComparableComparator();

  private int size;
  private Comparator comparator;
  private Object[] storage;

  public BinaryHeap() {
    this(DEFAULT_COMPARATOR, DEFAULT_CAPACITY);
  }

  public BinaryHeap(int capacity) {
    this(DEFAULT_COMPARATOR, capacity);
  }

  public BinaryHeap(Comparator comparator) {
    this(comparator, DEFAULT_CAPACITY);
  }

  public BinaryHeap(Comparator comparator, int capacity) {
    this.comparator = comparator;
    size = 0;
    storage = new Comparable[capacity + 1];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void add(Comparable item) {
    if (size == storage.length) {
      Comparable[] newHeap = new Comparable[2 * storage.length];
      System.arraycopy(storage, 0, newHeap, 0, storage.length);
      storage = newHeap;
    }
    int parent, child = size++; // the next available slot in the heap
    while (child > 0
        && comparator.compare(storage[parent = (child - 1) / 2], item) < 0) {
      storage[child] = storage[parent];
      child = parent;
    }
    storage[child] = item;
  }

  public Object removeFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    Object result = storage[0];
    Object item = storage[--size];
    int child, parent = 0;
    while ((child = (2 * parent) + 1) < size) {
      if (child + 1 < size
          && comparator.compare(storage[child], storage[child + 1]) < 0) {
        ++child;
      }
      if (comparator.compare(item, storage[child]) < 0) {
        storage[parent] = storage[child];
        parent = child;
      } else {
        break;
      }
    }
    storage[parent] = item;
    return result;
  }

}