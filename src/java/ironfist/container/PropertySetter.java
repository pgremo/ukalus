package ironfist.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PropertySetter {

  private Map<String, Object> properties = new HashMap<String, Object>();
  private Registry registry;

  public PropertySetter(Map<String, Object> properties, Registry registry) {
    this.properties = properties;
    this.registry = registry;
  }

  public void setProperties(Object input) throws IllegalArgumentException,
      IllegalAccessException, InvocationTargetException {
    new Sequence<Method>(input.getClass()
      .getMethods()).doWhile(new SetProperty(input, properties, registry));
  }
}
