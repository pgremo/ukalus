/**
 * 
 */
package ironfist.container;

import ironfist.util.Closure;

import java.lang.reflect.Method;
import java.util.Map;

final class SetProperty implements Closure<Method, Boolean> {

  private static final String SETTER_PREFIX = "set";
  private Object input;
  private Map<String, Resolver> values;
  private int count;

  public SetProperty(Object input, Map<String, Resolver> values) {
    this.input = input;
    this.values = values;
  }

  public Boolean apply(Method method) {
    String name = method.getName();
    if (name.startsWith(SETTER_PREFIX)) {
      Resolver property = values.get(name.substring(3)
        .toLowerCase());
      if (property != null) {
        count++;
        Class<?> type = method.getParameterTypes()[0];
        try {
          method.invoke(input, new Object[] { property.getValue(type) });
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return count < values.size();
  }
}