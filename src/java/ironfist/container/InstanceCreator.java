package ironfist.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InstanceCreator {

  private Class type;
  private Object[] arguments;
  private Registry registry;

  public InstanceCreator(Class type, String[] arguments, Registry registry) {
    this.type = type;
    this.arguments = arguments;
    this.registry = registry;
    if (arguments == null) {
      throw new NullPointerException();
    }
  }

  public Object newInstance() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor constructor = new Sequence<Constructor>(
      type.getConstructors()).detect(new ConstructorParameterLengthMatches());
    Object[] values = new Object[arguments.length];
    Class[] types = constructor.getParameterTypes();
    for (int i = 0; i < arguments.length; i++) {
      values[i] = registry.getConverter(types[i])
        .transform(arguments[i]);
    }
    return constructor.newInstance(values);
  }

  private final class ConstructorParameterLengthMatches
      implements
        Predicate<Constructor> {

    public boolean invoke(Constructor item) {
      return item.getParameterTypes().length == arguments.length;
    }
  }

}
