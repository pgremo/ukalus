package ukalus.container;

import ukalus.util.Closure;

import java.lang.reflect.Method;
import java.util.Map;

public class SetProperty
    implements
      Closure<Map.Entry<String, Resolver>, Object> {

  private static final long serialVersionUID = 3906363822712435255L;
  private Map<String, Method> setters;
  private Object input;

  public SetProperty(Map<String, Method> setters, Object input) {
    this.setters = setters;
    this.input = input;
  }

  public Object apply(Map.Entry<String, Resolver> property) {
    Method method = setters.get(property.getKey());
    if (method != null) {
      try {
        method.invoke(input, new Object[]{property.getValue()
          .getValue(method.getParameterTypes()[0])});
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

}
