package ironfist;

import java.util.Comparator;

/**
 * @author pmgremo
 * 
 */
public class ReverseIntegerComparator implements Comparator {

	/**
	 * @see java.util.Comparator#compare(Object, Object)
	 */
	public int compare(Object o1, Object o2) {
		return -(((Integer) o1).compareTo((Integer) o2));
	}

}