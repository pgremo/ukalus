package ironfist.container;

import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {

  private InstanceCreator creator;
  private PropertySetter setter;
  private Object instance;

  public ObjectFactory(InstanceCreator creator, PropertySetter setter) {
    this.creator = creator;
    if (creator == null) {
      throw new NullPointerException("creator can not be null");
    }
    this.setter = setter;
    if (setter == null) {
      throw new NullPointerException("setter can not be null");
    }
  }

  public Object getInstance() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    if (instance == null) {
      instance = creator.newInstance();
      setter.setProperties(instance);
    }
    return instance;
  }

}
