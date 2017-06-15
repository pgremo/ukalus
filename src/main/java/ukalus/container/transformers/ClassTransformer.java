package ukalus.container.transformers;

import ukalus.util.Closure;

public class ClassTransformer implements Closure<String, Class<?>> {

  private static final long serialVersionUID = 3835157259381453110L;

  public Class<?> apply(String value) {
    Class<?> result = null;
    try {
      result = Class.forName(value);
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException(value + " not found");
    }
    return result;
  }

}
