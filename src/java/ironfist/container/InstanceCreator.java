package ironfist.container;

import ironfist.util.Loop;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InstanceCreator {

  private Class type;
  private Resolver[] arguments;

  public InstanceCreator(Class type, Resolver[] arguments) {
    this.type = type;
    this.arguments = arguments;
    if (arguments == null) {
      throw new NullPointerException();
    }
  }

  public Object newInstance() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor constructor = new Loop<Constructor>(type.getConstructors()).detect(new ConstructorParameterLengthMatches(
        arguments));
    Object[] values = new Object[arguments.length];
    Class<?>[] types = constructor.getParameterTypes();
    for (int i = 0; i < arguments.length; i++) {
      values[i] = arguments[i].getValue(types[i]);
    }
    return constructor.newInstance(values);
  }

}
