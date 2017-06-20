package ukalus.container.creators;

import ukalus.container.InstanceCreator;
import ukalus.container.Resolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.PUBLIC;

public class InstanceMethodCreator implements InstanceCreator {

  private Resolver resolver;
  private String name;
  private Resolver[] arguments;

  public InstanceMethodCreator(Resolver resolver, String name,
                               Resolver[] arguments) {
    this.resolver = resolver;
    this.name = name;
    this.arguments = arguments;
    if (arguments == null) {
      throw new NullPointerException();
    }
  }

  public Object newInstance() throws IllegalArgumentException,
    InstantiationException, IllegalAccessException, InvocationTargetException {
    Object value = resolver.getValue(Object.class);
    Method method = Stream.of(value.getClass().getMethods()).filter(new MethodMatches(name, PUBLIC, arguments.length)).findFirst().orElse(null);
    Class<?>[] types = method.getParameterTypes();

    Object[] values = new Object[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
      values[i] = arguments[i].getValue(types[i]);
    }

    return method.invoke(value, values);
  }

}
