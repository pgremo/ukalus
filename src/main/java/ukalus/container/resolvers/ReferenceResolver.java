package ukalus.container.resolvers;

import ukalus.container.ObjectRegistry;
import ukalus.container.Resolver;

import java.lang.reflect.InvocationTargetException;

public class ReferenceResolver implements Resolver {

  private String id;
  private ObjectRegistry registry;

  public ReferenceResolver(String id, ObjectRegistry registry) {
    this.id = id;
    this.registry = registry;
  }

  public Object getValue(Class<?> type) throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    Object result = registry.getObject(id);
    if (!type.isAssignableFrom(result.getClass())) {
      throw new IllegalArgumentException(id + " is not assignable to " + type);
    }
    return result;
  }

}
