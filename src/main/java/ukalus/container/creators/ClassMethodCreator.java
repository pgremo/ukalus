package ukalus.container.creators;

import ukalus.container.InstanceCreator;
import ukalus.container.Resolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;

public class ClassMethodCreator implements InstanceCreator {

  private Class type;
  private String name;
  private Resolver[] arguments;

  public ClassMethodCreator(Class type, String name, Resolver[] arguments) {
    this.type = type;
    this.name = name;
    this.arguments = arguments;
    if (arguments == null) {
      throw new NullPointerException();
    }
  }

  public Object newInstance() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Method method = Stream.of(type.getMethods()).filter(new MethodMatches(name, PUBLIC + STATIC, arguments.length)).findFirst().orElse(null);
    Class<?>[] types = method.getParameterTypes();

    Object[] values = new Object[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
      values[i] = arguments[i].getValue(types[i]);
    }

    return method.invoke(type, values);
  }

}
