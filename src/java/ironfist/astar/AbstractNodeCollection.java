/*
 * Created on Mar 2, 2005
 *
 */
package ironfist.astar;

import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * @author gremopm
 *  
 */
public abstract class AbstractNodeCollection extends AbstractCollection {

  public Object get(Object o) {
    Object result = null;
    Iterator iterator = iterator();
    while (iterator.hasNext() && result == null) {
      Object current = iterator.next();
      if (current.equals(o)) {
        result = current;
      }
    }
    return result;
  }

}