/*
 * Created on Feb 10, 2005
 *
  */
package ironfist.util;

import java.util.Comparator;


/**
 * @author gremopm
 *
 */
public class ComparableComparator implements Comparator {

  public int compare(Object o1, Object o2) {
    return ((Comparable)o1).compareTo(o2);
  }

}
