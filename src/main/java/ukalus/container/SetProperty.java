package ukalus.container;

import java.util.function.Consumer;
import java.util.function.Function;

import java.lang.reflect.Method;
import java.util.Map;

public class SetProperty implements Function<Map.Entry<String, Resolver>, Object>, Consumer<Map.Entry<String, Resolver>> {

  private Map<String, Method> setters;
  private Object input;

  public SetProperty(Map<String, Method> setters, Object input) {
    this.setters = setters;
    this.input = input;
  }

  public Object apply(Map.Entry<String, Resolver> property) {
    accept(property);
    return null;
  }

  @Override
  public void accept(Map.Entry<String, Resolver> property) {
    Method method = setters.get(property.getKey());
    if (method != null) {
      try {
        method.invoke(input, property.getValue().getValue(method.getParameterTypes()[0]));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
