/*
 * Created on Mar 2, 2005
 *
 */
package ironfist.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author gremopm
 *  
 */
public final class Collections {

  private Collections() {

  }

  public static final Iterator EMPTY_ITERATOR = new EmptyIterator();

  private static class EmptyIterator implements Iterator {

    public boolean hasNext() {
      return false;
    }

    public Object next() {
      throw new NoSuchElementException();
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

}