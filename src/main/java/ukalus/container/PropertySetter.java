package ukalus.container;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PropertySetter {

  private Map<String, Resolver> properties;

  public PropertySetter(Map<String, Resolver> properties) {
    this.properties = properties;
  }

  public void setProperties(Object input) throws IllegalArgumentException {
    Method[] methods = input.getClass().getMethods();
    Map<String, Method> setters = new HashMap<>(methods.length);
    Stream.of(methods).forEach(new SetterCollector(setters));
    properties.entrySet().forEach(new SetProperty(setters, input));
  }
}
