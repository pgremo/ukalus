package ironfist.container.creators;

import ironfist.container.InstanceCreator;
import ironfist.container.ObjectRegistry;
import ironfist.container.Resolver;
import ironfist.container.resolvers.ValueResolver;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import junit.framework.TestCase;

public class ClassMethodCreatorTest extends TestCase {

  public void test0ArgMethod() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException,
      InvocationTargetException, UnknownHostException {
    InetAddress expected = InetAddress.getLocalHost();
    InstanceCreator creator = new ClassMethodCreator(InetAddress.class,
      "getLocalHost", new Resolver[0]);
    Object actual = creator.newInstance();
    assertEquals(expected, actual);
  }

  public void test1ArgMethod() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException,
      InvocationTargetException, UnknownHostException {
    InetAddress expected = InetAddress.getByName("www.google.com");
    InstanceCreator creator = new ClassMethodCreator(InetAddress.class,
      "getByName", new Resolver[]{new ValueResolver(new ObjectRegistry(),
        "www.google.com")});
    Object actual = creator.newInstance();
    assertEquals(expected, actual);
  }

  public void test1IntArgMethod() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException,
      InvocationTargetException, UnknownHostException {
    ByteBuffer expected = ByteBuffer.allocate(1024);
    InstanceCreator creator = new ClassMethodCreator(ByteBuffer.class,
      "allocate",
      new Resolver[]{new ValueResolver(new ObjectRegistry(), "1024")});
    ByteBuffer actual = (ByteBuffer) creator.newInstance();
    assertEquals(expected.capacity(), actual.capacity());
  }

}
