/*
 * Created on Mar 2, 2005
 *
 */
package ironfist.astar;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author gremopm
 *  
 */
public class CloseList extends AbstractNodeCollection {

  private LinkedList storage = new LinkedList();

  public int size() {
    return storage.size();
  }

  public Iterator iterator() {
    return storage.iterator();
  }

  public boolean add(Object o) {
    return storage.add(o);
  }

}