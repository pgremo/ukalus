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
public class ComparableComparator implements Comparator<Comparable<Object>> {

  public int compare(Comparable<Object> o1, Comparable<Object> o2) {
    return o1.compareTo(o2);
  }

}
