package ironfist.container;

import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {

  private InstanceCreator creator;
  private PropertySetter setter;
  private Object instance;

  public ObjectFactory(InstanceCreator creator, PropertySetter setter) {
    this.creator = creator;
    this.setter = setter;
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
