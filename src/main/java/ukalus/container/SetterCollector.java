package ukalus.container;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Consumer;

public class SetterCollector implements Consumer<Method> {

  private Map<String, Method> setters;

  public SetterCollector(Map<String, Method> setters) {
    this.setters = setters;
  }

  @Override
  public void accept(Method method) {
    String name = method.getName();
    if (name.startsWith("set") && method.getParameterTypes().length == 1) {
      setters.put(Character.toLowerCase(name.charAt(3)) + name.substring(4), method);
    }
  }
}
