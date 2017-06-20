package ukalus.container.creators;

import ukalus.container.InstanceCreator;
import ukalus.container.Resolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class ConstructorCreator implements InstanceCreator {

  private Class type;
  private Resolver[] arguments;

  public ConstructorCreator(Class type, Resolver[] arguments) {
    this.type = type;
    this.arguments = arguments;
    if (arguments == null) {
      throw new NullPointerException();
    }
  }

  public Object newInstance() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor constructor = Stream.of(type.getConstructors()).filter(new ConstructorMatches(arguments.length)).findFirst().orElse(null);
    Class<?>[] types = constructor.getParameterTypes();

    Object[] values = new Object[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
      values[i] = arguments[i].getValue(types[i]);
    }
    
    return constructor.newInstance(values);
  }

}
