package ironfist.container;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class SetPropertyTest extends TestCase {

  private String stringValue;
  private int intValue;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    stringValue = null;
    intValue = 0;
  }

  @Override
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
    Map<String, Resolver> properties = new HashMap<String, Resolver>();
    properties.put("value", new Value(new Registry(), expected));
    SetProperty setter = new SetProperty(this, properties);
    Method method = getClass().getMethod("setValue", new Class[]{String.class});
    setter.apply(method);
    assertEquals(expected, stringValue);
  }

  public void testInvokeInt() throws SecurityException, NoSuchMethodException {
    int expected = 33;
    Map<String, Resolver> properties = new HashMap<String, Resolver>();
    properties.put("value", new Value(new Registry(), "33"));
    SetProperty setter = new SetProperty(this, properties);
    Method method = getClass().getMethod("setValue", new Class[]{Integer.TYPE});
    setter.apply(method);
    assertEquals(expected, intValue);
  }

}
