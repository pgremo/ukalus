package ukalus.container.creators;

import ukalus.container.InstanceCreator;
import ukalus.container.Resolver;
import ukalus.util.Loop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static java.lang.reflect.Modifier.*;

public class ClassMethodCreator implements InstanceCreator{

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

  public Object newInstance() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    Method method = new Loop<Method>(type.getMethods()).detect(new MethodMatches(
      name, PUBLIC + STATIC, arguments.length));
    Class<?>[] types = method.getParameterTypes();

    Object[] values = new Object[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
      values[i] = arguments[i].getValue(types[i]);
    }

    return method.invoke(type, values);
  }

}
