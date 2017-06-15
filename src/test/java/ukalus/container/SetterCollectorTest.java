package ukalus.container;

import ukalus.util.Loop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class SetterCollectorTest extends TestCase {

  public void testCollectionSetters() throws SecurityException,
      NoSuchMethodException {
    Map<String, Method> expected = new HashMap<String, Method>();
    expected.put("x", TestClass.class.getMethod("setX",
      new Class[]{Object.class}));
    expected.put("someThingElse", TestClass.class.getMethod("setSomeThingElse",
      new Class[]{Object.class}));
    Map<String, Method> actual = new HashMap<String, Method>();
    SetterCollector collector = new SetterCollector(actual);
    new Loop<Method>(TestClass.class.getMethods()).forEach(new SetterCollector(
      actual));
    assertEquals(expected, actual);
  }

  class TestClass {

    public void setX(Object value) {

    }

    public void setSomeThingElse(Object value) {

    }

    public void setInvalid(Object value, Object value2) {

    }

    public void nonSetter(Object value) {

    }
  }

}
