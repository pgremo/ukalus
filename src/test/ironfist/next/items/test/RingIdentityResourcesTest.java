package ironfist.next.items.test;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class RingIdentityResourcesTest extends TestCase {

  /**
   * Constructor for RingIdentityResourcesTest.
   * 
   * @param arg0
   */
  public RingIdentityResourcesTest(String arg0) {
    super(arg0);
  }

  public void testLoadResource() throws Exception {
    ResourceBundle bundle = ResourceBundle.getBundle("ironfist.next.items.RingIdentityResources");
    String pattern = bundle.getString("ring.protection.identity");
    System.out.println(MessageFormat.format(pattern, new Object[]{new Integer(
      -1)}));
    System.out.println(MessageFormat.format(pattern, new Object[]{
        new Integer(1),
        new Integer(-1)}));
    System.out.println(MessageFormat.format(pattern, new Object[]{
        new Integer(1),
        new Integer(+12)}));
    System.out.println(MessageFormat.format(pattern, new Object[]{
        new Integer(2),
        new Integer(0)}));
    System.out.println(MessageFormat.format(pattern, new Object[]{
        new Integer(3),
        new Integer(+2)}));
  }
}