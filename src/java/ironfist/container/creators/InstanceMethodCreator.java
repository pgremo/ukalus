package ironfist.container.creators;

import static java.lang.reflect.Modifier.PUBLIC;
import ironfist.container.InstanceCreator;
import ironfist.container.Resolver;
import ironfist.util.Loop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    Method method = new Loop<Method>(value.getClass()
      .getMethods()).detect(new MethodMatches(name, PUBLIC, arguments.length));
    Class<?>[] types = method.getParameterTypes();

    Object[] values = new Object[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
      values[i] = arguments[i].getValue(types[i]);
    }

    return method.invoke(value, values);
  }

}
