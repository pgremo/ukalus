/*
 * Created on Mar 20, 2005
 *
 */
package ironfist.container;

import java.lang.reflect.InvocationTargetException;

public interface Resolver {

  Object getValue(Class<?> type) throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException;

}
