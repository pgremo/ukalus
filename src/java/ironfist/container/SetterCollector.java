package ironfist.container;

import ironfist.util.Closure;

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
    if (name.startsWith("set")) {
      setters.put(name.substring(3)
        .toLowerCase(), method);
    }
    return null;
  }

}
