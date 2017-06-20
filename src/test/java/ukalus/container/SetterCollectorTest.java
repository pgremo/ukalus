package ukalus.container;

import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SetterCollectorTest extends TestCase {

  public void testCollectionSetters() throws SecurityException, NoSuchMethodException {
    Map<String, Method> expected = new HashMap<>();
    expected.put("x", TestClass.class.getMethod("setX", Object.class));
    expected.put("someThingElse", TestClass.class.getMethod("setSomeThingElse", Object.class));
    Map<String, Method> actual = new HashMap<>();
    Stream.of(TestClass.class.getMethods()).forEach(new SetterCollector(actual));
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
