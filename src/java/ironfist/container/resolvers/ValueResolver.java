/*
 * Created on Mar 20, 2005
 *
 */
package ironfist.container.resolvers;

import ironfist.container.ObjectRegistry;
import ironfist.container.Resolver;
import ironfist.util.Closure;

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
