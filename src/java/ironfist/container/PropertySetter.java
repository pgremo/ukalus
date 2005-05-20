package ironfist.container;

import ironfist.util.Loop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PropertySetter {

  private Map<String, Resolver> properties;

  public PropertySetter(Map<String, Resolver> properties) {
    this.properties = properties;
  }

  public void setProperties(Object input) throws IllegalArgumentException {
    Method[] methods = input.getClass()
      .getMethods();
    Map<String, Method> setters = new HashMap<String, Method>(methods.length);
    new Loop<Method>(methods).forEach(new SetterCollector(setters));
    new Loop<Map.Entry<String, Resolver>>(properties.entrySet()).forEach(new SetProperty(
      setters, input));
  }
}
