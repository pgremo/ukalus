package ironfist.items;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class RingIdentityResourcesTest extends TestCase {

  public void testLoadResource() throws Exception {
    ResourceBundle bundle = ResourceBundle.getBundle("ironfist.items.RingIdentityResources");
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