package ironfist.container.creators;

import ironfist.container.InstanceCreator;
import ironfist.container.ObjectRegistry;
import ironfist.container.Resolver;
import ironfist.container.resolvers.ValueResolver;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import junit.framework.TestCase;

public class InstanceMethodCreatorTest extends TestCase {

  private boolean arg0Called;

  public Object createInstance0Arg() {
    arg0Called = true;
    return this;
  }

  public void test0ArgCreator() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    InstanceCreator creator = new InstanceMethodCreator(new ValueResolver(
      new ObjectRegistry(), this), "createInstance0Arg", new Resolver[0]);
    Object actual = creator.newInstance();
    assertEquals(this, actual);
    assertTrue(arg0Called);
  }

  private boolean arg1Called;
  private Object[] arg1Args;

  public Object createInstance1Arg(String value) {
    arg1Called = true;
    arg1Args = new Object[]{value};
    return this;
  }

  public void test1ArgCreator() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    ObjectRegistry registry = new ObjectRegistry();
    InstanceCreator creator = new InstanceMethodCreator(new ValueResolver(
      registry, this), "createInstance1Arg", new Resolver[]{new ValueResolver(
      registry, "something")});
    Object actual = creator.newInstance();
    assertEquals(this, actual);
    assertTrue(arg1Called);
    assertTrue(Arrays.equals(new Object[]{"something"}, arg1Args));
  }

  private boolean arg2Called;
  private Object[] arg2Args;

  public Object createInstance2Arg(int value) {
    arg2Called = true;
    arg2Args = new Object[]{value};
    return this;
  }

  public void test1ArgIntCreator() throws IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    ObjectRegistry registry = new ObjectRegistry();
    InstanceCreator creator = new InstanceMethodCreator(new ValueResolver(
      registry, this), "createInstance2Arg", new Resolver[]{new ValueResolver(
      registry, "1")});
    Object actual = creator.newInstance();
    assertEquals(this, actual);
    assertTrue(arg2Called);
    assertTrue(Arrays.equals(new Object[]{1}, arg2Args));
  }

}
