/*
 * Created on Mar 8, 2005
 *
 */
package ironfist.loop;

import java.util.Comparator;

/**
 * @author gremopm
 * 
 */
public class TurnComparator implements Comparator<Action> {

  public int compare(Action o1, Action o2) {
    int t1 = o1.getTurn();
    int t2 = o2.getTurn();
    return t1 < t2 ? -1 : (t1 == t2 ? 0 : 1);
  }

}