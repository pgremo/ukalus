/*
 * Created on Sep 27, 2004
 *
 */
package ironfist.persistence;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author gremopm
 * 
 */
public class SerializedObjectIterator<E> implements Iterator<E> {

  private RandomAccessFile channel;
  private byte[] data = new byte[0];
  private Object next;

  public SerializedObjectIterator(RandomAccessFile channel) {
    this.channel = channel;
    loadNext();
  }

  public boolean hasNext() {
    return next != null;
  }

  public E next() {
    if (next == null) {
      throw new NoSuchElementException();
    }
    E result = (E) next;
    loadNext();
    return result;
  }

  private void loadNext() {
    next = null;
    try {
      int size = channel.readInt();
      if (size > data.length) {
        data = new byte[size];
      }
      if (channel.read(data, 0, size) == size) {
        ObjectInputStream stream = new ObjectInputStream(
          new ByteArrayInputStream(data, 0, size));
        next = stream.readObject();
      }
    } catch (EOFException e) {
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }
}