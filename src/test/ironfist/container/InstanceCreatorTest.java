package ironfist.container;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

public class InstanceCreatorTest extends TestCase {

  private Registry registry = new Registry();

  public void test0ArgConstructor() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    InstanceCreator creator = new InstanceCreator(Object.class, new Resolver[0]);
    assertNotNull(creator.newInstance());
  }

  public void test1ArgConstructor() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    InstanceCreator creator = new InstanceCreator(Integer.class,
        new Resolver[] { new Value(registry, "1") });
    Object actual = creator.newInstance();
    assertNotNull(actual);
    assertEquals(new Integer(1), actual);
  }

  public void test4ArgConstructorWithNonStrings()
      throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException, MalformedURLException {
    InstanceCreator creator = new InstanceCreator(URL.class, new Resolver[] {
        new Value(registry, "http"),
        new Value(registry, "www.foo.com"),
        new Value(registry, "80"),
        new Value(registry, "/stuff") });
    Object actual = creator.newInstance();
    assertNotNull(actual);
    assertEquals(new URL("http://www.foo.com:80/stuff"), actual);
  }

}
