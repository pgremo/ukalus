/*
 * Created on Mar 20, 2005
 *
 */
package ironfist.container.resolvers;

import ironfist.container.ObjectRegistry;
import ironfist.container.Resolver;

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
      result = registry.getConverter(type)
        .apply(value);
    }
    return result;
  }

}
