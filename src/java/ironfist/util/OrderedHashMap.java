/*
 * Created on Mar 1, 2005
 *
 */
package ironfist.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author gremopm
 *  
 */
public class OrderedHashMap extends AbstractMap implements Serializable {

  private static final int DEFAULT_INITIAL_CAPACITY = 16;
  private static final Object NULL = new Object();

  private transient OrderedHashMapEntry[] entries;
  private transient OrderedHashMapEntry head = new OrderedHashMapEntry();
  private transient OrderedHashMapEntry tail = new OrderedHashMapEntry();
  private transient OrderedHashMap old;
  private transient int size;

  private transient Set entrySet = new EntrySet();
  private transient Set keys = new KeySet();
  private transient Collection values = new Values();

  public OrderedHashMap() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  public OrderedHashMap(int initialCapacity) {
    int capacity = DEFAULT_INITIAL_CAPACITY;
    while (capacity < initialCapacity) {
      capacity <<= 1;
    }
    entries = new OrderedHashMapEntry[capacity];
    head.after = tail;
    tail.before = head;
  }

  public int size() {
    return size;
  }

  public void clear() {
    for (OrderedHashMapEntry entry = head.after; entry != tail; entry = entry.after) {
      entry.key = null;
      entry.value = null;
      if (entry.previous == null) {
        entry.table[entry.index] = null;
      }
    }
    tail = head.after;
    size = 0;
    old = null;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean containsKey(Object key) {
    Object target = (key == null) ? NULL : key;
    OrderedHashMapEntry entry = getEntry(key, target.hashCode());
    return entry != null;
  }

  private OrderedHashMapEntry getEntry(Object key, int hash) {
    OrderedHashMapEntry result = null;
    OrderedHashMapEntry entry = entries[hash & (entries.length - 1)];
    while (entry != null && result == null) {
      if ((key == entry.key) || ((hash == entry.hash) && key.equals(entry.key))) {
        result = entry;
      }
      entry = entry.next;
    }
    if (result == null && old != null) {
      result = old.getEntry(key, hash);
    }
    return result;
  }

  private void addEntry(Object key, Object value, int hash) {
    OrderedHashMapEntry entry = tail;

    int index = hash & (entries.length - 1);
    entry.key = key;
    entry.value = value;
    entry.hash = hash;
    entry.table = entries;
    entry.index = index;

    OrderedHashMapEntry next = entries[index];
    entry.next = next;
    if (next != null) {
      next.previous = entry;
    }
    entry.previous = null;
    entries[index] = entry;

    size++;

    tail = entry.after;

    if (tail == null) {
      tail = new OrderedHashMapEntry();
      tail.before = entry;
      entry.after = tail;
      if (size >= entries.length) {
        increaseCapacity();
      }
    }
  }

  private void increaseCapacity() {
    OrderedHashMap oldMap = new OrderedHashMap(entries.length << 1);
    OrderedHashMapEntry[] newEntries = oldMap.entries;
    oldMap.entries = entries;
    entries = newEntries;

    oldMap.old = old;
    oldMap.head = head;
    oldMap.tail = tail;
    oldMap.size = size;

    old = oldMap;
  }

  private void removeEntry(OrderedHashMapEntry entry) {
    entry.key = null;
    entry.value = null;

    OrderedHashMapEntry previous = entry.previous;
    OrderedHashMapEntry next = entry.next;
    if (previous == null) {
      entry.table[entry.index] = next;
    } else {
      previous.next = next;
    }
    if (next != null) {
      next.previous = previous;
    }

    entry.before.after = entry.after;
    entry.after.before = entry.before;

    OrderedHashMapEntry before = entry.before = tail.before;
    entry.after = tail;
    before.after = entry;
    tail.before = entry;

    tail = entry;

    size--;
  }

  public boolean containsValue(Object value) {
    boolean found = false;
    for (OrderedHashMapEntry entry = head.after; entry != tail && !found; entry = entry.after) {
      if (value == entry.value || (value != null && value.equals(entry.value))) {
        found = true;
      }
    }
    return found;
  }

  public Collection values() {
    return values;
  }

  public void putAll(Map map) {
    Iterator iterator = map.entrySet()
      .iterator();
    while (iterator.hasNext()) {
      Entry entry = (Entry) iterator.next();
      put(entry.getKey(), entry.getValue());
    }
  }

  public Set entrySet() {
    return entrySet;
  }

  public Set keySet() {
    return keys;
  }

  public Object get(Object key) {
    Object target = (key == null) ? NULL : key;
    OrderedHashMapEntry entry = getEntry(key, target.hashCode());
    return (entry == null) ? null : entry.value;
  }

  public Object remove(Object key) {
    Object target = (key == null) ? NULL : key;
    OrderedHashMapEntry entry = getEntry(key, target.hashCode());
    Object result = null;
    if (entry != null) {
      result = entry.value;
    } else {
      removeEntry(entry);
    }
    return result;
  }

  public Object put(Object key, Object value) {
    Object target = (key == null) ? NULL : key;
    int hash = target.hashCode();
    OrderedHashMapEntry entry = getEntry(key, hash);
    Object result = null;
    if (entry != null) {
      result = entry.value;
      entry.value = value;
    } else {
      addEntry(key, value, hash);
    }
    return result;
  }

  private void readObject(ObjectInputStream stream) throws IOException,
      ClassNotFoundException {
    stream.defaultReadObject();
    final int size = stream.readInt();
    final int entriesLength = stream.readInt();

    entries = new OrderedHashMapEntry[entriesLength];
    head = new OrderedHashMapEntry();
    tail = new OrderedHashMapEntry();
    head.after = tail;
    tail.before = head;
    values = new Values();
    entrySet = new EntrySet();
    keys = new KeySet();

    for (int i = 0; i < size; i++) {
      Object key = stream.readObject();
      Object value = stream.readObject();
      Object target = (key == null) ? NULL : key;
      addEntry(target, value, target.hashCode());
    }
  }

  private void writeObject(ObjectOutputStream stream) throws IOException {
    stream.defaultWriteObject();
    stream.writeInt(size);
    stream.writeInt(entries.length);
    for (OrderedHashMapEntry entry = head.after; entry != tail; entry = entry.after) {
      if (entry.key.equals(NULL)) {
        stream.writeObject(null);
      } else {
        stream.writeObject(entry.key);
      }
      stream.writeObject(entry.value);
    }
  }

  private class OrderedHashMapEntry implements Map.Entry {

    Object key;
    Object value;

    OrderedHashMapEntry next;
    OrderedHashMapEntry previous;

    OrderedHashMapEntry before;
    OrderedHashMapEntry after;

    int hash;

    OrderedHashMapEntry[] table;
    int index;

    public Object getKey() {
      return key;
    }

    public Object getValue() {
      return value;
    }

    public Object setValue(Object value) {
      Object result = this.value;
      this.value = value;
      return result;
    }
  }

  private final class EntrySet extends AbstractSet {

    public Iterator iterator() {
      EntryIterator i = new EntryIterator();
      i.map = OrderedHashMap.this;
      i.after = head.after;
      i.end = tail;
      return i;
    }

    public int size() {
      return size;
    }

    public void clear() {
      OrderedHashMap.this.clear();
    }

    public boolean contains(Object obj) {
      boolean result = false;
      if (obj instanceof Entry && obj != null) {
        Entry entry = (Entry) obj;
        Object target = (entry.getKey() == null) ? NULL : entry.getKey();
        int hash = target.hashCode();
        Entry mapEntry = getEntry(target, hash);
        result = entry.equals(mapEntry);
      }
      return result;
    }

    public boolean remove(Object obj) {
      boolean result = false;
      if (obj instanceof Entry && obj != null) {
        Entry entry = (Entry) obj;
        Object target = (entry.getKey() == null) ? NULL : entry.getKey();
        int hash = target.hashCode();
        OrderedHashMapEntry mapEntry = getEntry(target, hash);
        if (mapEntry != null && entry.equals(mapEntry)) {
          removeEntry(mapEntry);
          result = true;
        }
      }
      return result;
    }

  }

  private static final class EntryIterator implements Iterator {

    OrderedHashMap map;
    OrderedHashMapEntry after;
    OrderedHashMapEntry end;

    public void remove() {
      if (after.before == null) {
        throw new IllegalStateException();
      }
      map.removeEntry(after.before);
    }

    public boolean hasNext() {
      return after != end;
    }

    public Object next() {
      if (after == end) {
        throw new NoSuchElementException();
      }
      OrderedHashMapEntry entry = after;
      after = entry.after;
      return entry;
    }
  }
  private final class KeySet extends AbstractSet {

    public Iterator iterator() {
      KeyIterator i = new KeyIterator();
      i.map = OrderedHashMap.this;
      i.after = head.after;
      i.end = tail;
      return i;
    }

    public int size() {
      return size;
    }

    public void clear() {
      OrderedHashMap.this.clear();
    }

    public boolean contains(Object obj) {
      return OrderedHashMap.this.containsKey(obj);
    }

    public boolean remove(Object obj) {
      return OrderedHashMap.this.remove(obj) != null;
    }
  }

  private static final class KeyIterator implements Iterator {

    OrderedHashMap map;
    OrderedHashMapEntry after;
    OrderedHashMapEntry end;

    public void remove() {
      if (after.before != null) {
        map.removeEntry(after.before);
      } else {
        throw new IllegalStateException();
      }
    }

    public boolean hasNext() {
      return after != end;
    }

    public Object next() {
      if (after == end) {
        throw new NoSuchElementException();
      }
      OrderedHashMapEntry entry = after;
      after = entry.after;
      return entry.key;
    }
  }
  private class Values extends AbstractCollection {

    public Iterator iterator() {
      ValueIterator i = new ValueIterator();
      i.map = OrderedHashMap.this;
      i.after = head.after;
      i.end = tail;
      return i;
    }

    public int size() {
      return size;
    }

    public boolean contains(Object o) {
      return containsValue(o);
    }

    public void clear() {
      OrderedHashMap.this.clear();
    }

  }

  private static final class ValueIterator implements Iterator {

    OrderedHashMap map;
    OrderedHashMapEntry after;
    OrderedHashMapEntry end;

    public void remove() {
      if (after.before == null) {
        throw new IllegalStateException();
      }
      map.removeEntry(after.before);
    }

    public boolean hasNext() {
      return after != end;
    }

    public Object next() {
      if (after == end) {
        throw new NoSuchElementException();
      }
      OrderedHashMapEntry entry = after;
      after = entry.after;
      return entry.value;
    }
  }

}