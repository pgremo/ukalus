package ironfist.container.creators;

import ironfist.container.ObjectRegistry;
import ironfist.container.Resolver;
import ironfist.container.resolvers.ValueResolver;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

public class ConstructorCreatorTest extends TestCase {

  private ObjectRegistry registry = new ObjectRegistry();

  public void test0ArgConstructor() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    ConstructorCreator creator = new ConstructorCreator(Object.class, new Resolver[0]);
    assertNotNull(creator.newInstance());
  }

  public void test1ArgConstructor() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    ConstructorCreator creator = new ConstructorCreator(Integer.class,
        new Resolver[] { new ValueResolver(registry, "1") });
    Object actual = creator.newInstance();
    assertNotNull(actual);
    assertEquals(new Integer(1), actual);
  }

  public void test4ArgConstructorWithNonStrings()
      throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException, MalformedURLException {
    ConstructorCreator creator = new ConstructorCreator(URL.class, new Resolver[] {
        new ValueResolver(registry, "http"),
        new ValueResolver(registry, "www.foo.com"),
        new ValueResolver(registry, "80"),
        new ValueResolver(registry, "/stuff") });
    Object actual = creator.newInstance();
    assertNotNull(actual);
    assertEquals(new URL("http://www.foo.com:80/stuff"), actual);
  }

}
