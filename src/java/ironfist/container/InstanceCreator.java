package ironfist.container;

import java.lang.reflect.InvocationTargetException;

public interface InstanceCreator {

  Object newInstance() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException;

}