/*
 * Created on Mar 8, 2005
 *
 */
package ukalus.loop;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author gremopm
 * 
 */
public class TickComparator implements Comparator<Event>, Serializable {

  private static final long serialVersionUID = 3258416110171863088L;

  public int compare(Event o1, Event o2) {
    int t1 = o1.getTick();
    int t2 = o2.getTick();
    return t1 < t2 ? -1 : (t1 == t2 ? 0 : 1);
  }

}