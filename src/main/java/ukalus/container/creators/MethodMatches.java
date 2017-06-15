package ukalus.container.creators;

import ukalus.util.Closure;

import java.lang.reflect.Method;

public class MethodMatches implements Closure<Method, Boolean> {

  private static final long serialVersionUID = 3617569401440843832L;

  private String name;
  private int modifiers;
  private int length;

  public MethodMatches(String name, int modifiers, int length) {
    this.name = name;
    this.modifiers = modifiers;
    this.length = length;
  }

  public Boolean apply(Method value) {
    return value.getModifiers() == modifiers && value.getName()
      .equals(name) && value.getParameterTypes().length == length;
  }

}
