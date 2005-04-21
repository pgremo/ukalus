package ironfist.items;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class RingDescriptionResourcesTest extends TestCase {

  public void testLoadResource() throws Exception {
    ResourceBundle bundle = ResourceBundle.getBundle("ironfist.items.RingDescriptionResources");
    Enumeration<String> keys = bundle.getKeys();

    keys = bundle.getKeys();

    String pattern = bundle.getString(keys.nextElement());
    System.out.println(MessageFormat.format(pattern,
      new Object[]{new Integer(0)}));
    System.out.println(MessageFormat.format(pattern,
      new Object[]{new Integer(1)}));
    System.out.println(MessageFormat.format(pattern, new Object[]{new Integer(
      30)}));
  }
}