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
public class ScrollDescriptionResourcesTest extends TestCase {

  public void testLoadResource() throws Exception {
    ResourceBundle bundle = ResourceBundle.getBundle(ScrollDescriptionResource.class.getName());
    Enumeration keys = bundle.getKeys();

    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      System.out.println(key + "=" + bundle.getString(key));
    }
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