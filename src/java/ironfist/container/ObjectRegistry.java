/*
 * Created on Jan 25, 2005
 *
 */
package ironfist.container;

import ironfist.container.transformers.BooleanTransformer;
import ironfist.container.transformers.ByteTransformer;
import ironfist.container.transformers.CharTransformer;
import ironfist.container.transformers.ClassTransformer;
import ironfist.container.transformers.DoubleTransformer;
import ironfist.container.transformers.FloatTransformer;
import ironfist.container.transformers.IntTransformer;
import ironfist.container.transformers.LongTransformer;
import ironfist.container.transformers.ShortTransformer;
import ironfist.container.transformers.StringTransformer;
import ironfist.util.Closure;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gremopm
 * 
 */
public class ObjectRegistry {

  private Map<Class, Closure> converters = new HashMap<Class, Closure>();
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

  public void addConverter(Class type, Closure converter) {
    converters.put(type, converter);
  }

  public Closure getConverter(Class type) {
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