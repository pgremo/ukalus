/*
 * Created on Jan 25, 2005
 *
 */
package ukalus.container;

import ukalus.container.transformers.BooleanTransformer;
import ukalus.container.transformers.ByteTransformer;
import ukalus.container.transformers.CharTransformer;
import ukalus.container.transformers.ClassTransformer;
import ukalus.container.transformers.DoubleTransformer;
import ukalus.container.transformers.FloatTransformer;
import ukalus.container.transformers.IntTransformer;
import ukalus.container.transformers.LongTransformer;
import ukalus.container.transformers.ShortTransformer;
import ukalus.container.transformers.StringTransformer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author gremopm
 * 
 */
public class ObjectRegistry {

  private Map<Class, Function> converters = new HashMap<Class, Function>();
  private Map<String, ObjectFactory> factories = new HashMap<String, ObjectFactory>();

  {
    converters.put(Boolean.TYPE, new BooleanTransformer());
    converters.put(Byte.TYPE, new ByteTransformer());
    converters.put(Character.TYPE, new CharTransformer());
    converters.put(Class.class, new ClassTransformer());
    converters.put(Double.TYPE, new DoubleTransformer());
    converters.put(Float.TYPE, new FloatTransformer());
    converters.put(Integer.TYPE, new IntTransformer());
    converters.put(Long.TYPE, new LongTransformer());
    converters.put(Object.class, new StringTransformer());
    converters.put(Short.TYPE, new ShortTransformer());
    converters.put(String.class, new StringTransformer());
  }

  public void addConverter(Class type, Function converter) {
    converters.put(type, converter);
  }

  public Function getConverter(Class type) {
    return converters.get(type);
  }

  public void addObject(String id, ObjectFactory factory) {
    factories.put(id, factory);
  }

  public Object getObject(String id) throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    Object result = null;
    ObjectFactory factory = factories.get(id);
    if (factory != null) {
      result = factory.getInstance();
    }
    return result;
  }

}