/*
 * Created on Feb 22, 2005
 *
 */
package ironfist.astar;

import java.util.Iterator;

/**
 * @author gremopm
 *  
 */
public class NodeIterator implements Iterator {

  private Node current;

  public NodeIterator(Node start) {
    this.current = start;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  public boolean hasNext() {
    return current != null;
  }

  public Object next() {
    Object result = current;
    current = current.getParent();
    return result;
  }

}