package ironfist.container.resolvers;

import ironfist.container.ObjectRegistry;
import ironfist.container.Resolver;

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
    return registry.getObject(id);
  }

}
