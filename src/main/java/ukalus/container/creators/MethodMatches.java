package ukalus.container.creators;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class MethodMatches implements Predicate<Method> {

  private String name;
  private int modifiers;
  private int length;

  public MethodMatches(String name, int modifiers, int length) {
    this.name = name;
    this.modifiers = modifiers;
    this.length = length;
  }

  public boolean test(Method value) {
    return value.getModifiers() == modifiers && value.getName()
      .equals(name) && value.getParameterTypes().length == length;
  }

}
