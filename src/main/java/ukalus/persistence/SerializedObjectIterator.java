/*
 * Created on Sep 27, 2004
 *
 */
package ukalus.persistence;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
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

  public SerializedObjectIterator(RandomAccessFile channel) {
    this.channel = channel;
  }

  public boolean hasNext() {
    try {
      return channel.getFilePointer() < channel.length();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public E next() {
    E result = null;
    try {
      int size = channel.readInt();
      if (size > data.length) {
        data = new byte[size];
      }
      if (channel.read(data, 0, size) == size) {
        ObjectInputStream stream = new ObjectInputStream(
          new ByteArrayInputStream(data, 0, size));
        result = (E) stream.readObject();
      }
    } catch (EOFException e) {
      throw new NoSuchElementException();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }
}