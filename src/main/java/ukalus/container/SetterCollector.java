package ukalus.container;

import ukalus.util.Closure;

import java.lang.reflect.Method;
import java.util.Map;

public class SetterCollector implements Closure<Method, Object> {

  private static final long serialVersionUID = 3617578219008833335L;
  private Map<String, Method> setters;

  public SetterCollector(Map<String, Method> setters) {
    this.setters = setters;
  }

  public Object apply(Method method) {
    String name = method.getName();
    if (name.startsWith("set") && method.getParameterTypes().length == 1) {
      setters.put(Character.toLowerCase(name.charAt(3)) + name.substring(4),
        method);
    }
    return null;
  }

}
