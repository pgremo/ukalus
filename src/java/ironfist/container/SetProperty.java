/**
 * 
 */
package ironfist.container;

import java.lang.reflect.Method;
import java.util.Map;

final class SetProperty implements Predicate<Method> {

  private Object input;
  private Map<String, Object> values;
  private Registry registry;
  private int count;

  public SetProperty(Object input, Map<String, Object> values, Registry registry) {
    this.input = input;
    this.values = values;
    this.registry = registry;
  }

  public boolean invoke(Method method) {
    String name = method.getName();
    if (name.startsWith("set")) {
      Object property = values.get(name.substring(3)
        .toLowerCase());
      if (property != null) {
        count++;
        Transformer converter = registry.getConverter(method.getParameterTypes()[0]);
        try {
          method.invoke(input, new Object[]{converter.transform(property)});
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return count < values.size();
  }
}