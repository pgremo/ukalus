/*
 * Created on Mar 20, 2005
 *
 */
package ironfist.container;

public class Value implements Resolver {
  private Registry registry;
  private Object value;

  public Value(Registry registry, Object value) {
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
