package ukalus.container;

import ukalus.container.resolvers.ValueResolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class SetPropertyTest extends TestCase {

  private String stringValue;
  private int intValue;

  protected void setUp() throws Exception {
    super.setUp();
    stringValue = null;
    intValue = 0;
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    stringValue = null;
    intValue = 0;
  }

  public void setValue(String value) {
    this.stringValue = value;
  }

  public void setValue(int value) {
    this.intValue = value;
  }

  public void testInvokeString() throws SecurityException,
      NoSuchMethodException {
    String expected = "some stuff";
    Map<String, Method> setters = new HashMap<String, Method>();
    setters.put("value", getClass().getMethod("setValue", String.class));
    SetProperty setter = new SetProperty(setters, this);
    setter.invoke(new Entry("value", new ValueResolver(new ObjectRegistry(),
      expected)));
    assertEquals(expected, stringValue);
  }

  public void testInvokeInt() throws SecurityException, NoSuchMethodException {
    int expected = 33;
    Map<String, Method> setters = new HashMap<String, Method>();
    setters.put("value", getClass().getMethod("setValue", Integer.TYPE));
    SetProperty setter = new SetProperty(setters, this);
    setter.invoke(new Entry("value", new ValueResolver(new ObjectRegistry(),
      "33")));
    assertEquals(expected, intValue);
  }

  private class Entry implements Map.Entry<String, Resolver> {

    public String name;
    private Resolver resolver;

    public Entry(String name, Resolver resolver) {
      this.name = name;
      this.resolver = resolver;
    }

    public String getKey() {
      return name;
    }

    public Resolver getValue() {
      return resolver;
    }

    public Resolver setValue(Resolver value) {
      return null;
    }

  }

}
