package ironfist.next.items;

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

  /**
   * Constructor for RingDescriptionResourcesTest.
   * 
   * @param arg0
   */
  public RingDescriptionResourcesTest(String arg0) {
    super(arg0);
  }

  public void testLoadResource() throws Exception {
    ResourceBundle bundle = ResourceBundle.getBundle("ironfist.next.items.RingDescriptionResources");
    Enumeration keys = bundle.getKeys();

    keys = bundle.getKeys();

    String pattern = bundle.getString((String) keys.nextElement());
    System.out.println(MessageFormat.format(pattern,
      new Object[]{new Integer(0)}));
    System.out.println(MessageFormat.format(pattern,
      new Object[]{new Integer(1)}));
    System.out.println(MessageFormat.format(pattern, new Object[]{new Integer(
      30)}));
  }
}