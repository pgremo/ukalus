/*
 * Created on Feb 26, 2005
 *
 */
package ironfist.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author pmgremo
 *  
 */
public final class Strings {
  private Strings() {

  }

  public static String join(Collection parts, String delimiter) {
    StringBuffer result = new StringBuffer();
    boolean first = true;
    Iterator iterator = parts.iterator();
    while (iterator.hasNext()) {
      if (first) {
        first = false;
      } else {
        result.append(delimiter);
      }
      result.append(iterator.next());
    }
    return result.toString();
  }

}