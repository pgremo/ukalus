package ironfist.container;

import ironfist.util.Loop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PropertySetter {

  private Map<String, Resolver> properties = new HashMap<String, Resolver>();

  public PropertySetter(Map<String, Resolver> properties) {
    this.properties = properties;
  }

  public void setProperties(Object input) throws IllegalArgumentException {
    new Loop<Method>(input.getClass()
      .getMethods()).doWhile(new SetProperty(input, properties));
  }
}
