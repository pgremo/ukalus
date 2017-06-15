/*
 * Created on Mar 20, 2005
 *
 */
package ukalus.container.resolvers;

import ukalus.container.ObjectRegistry;
import ukalus.container.Resolver;
import ukalus.util.Closure;

public class ValueResolver implements Resolver {

  private ObjectRegistry registry;
  private Object value;

  public ValueResolver(ObjectRegistry registry, Object value) {
    this.registry = registry;
    this.value = value;
  }

  public Object getValue(Class<?> type) {
    Object result = value;
    if (!type.isAssignableFrom(value.getClass())) {
      Closure converter = registry.getConverter(type);
      if (converter == null) {
        throw new IllegalArgumentException("converter not found for " + type);
      }
      result = converter.apply(value);
    }
    return result;
  }

}
