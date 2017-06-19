package ukalus.items;

import junit.framework.TestCase;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class ScrollDescriptionResourcesTest extends TestCase {
  public void testLoadResource() throws Exception {
    ResourceBundle bundle = ResourceBundle.getBundle(ScrollDescriptionResource.class.getName());
    Enumeration<String> keys = bundle.getKeys();

    while (keys.hasMoreElements()) {
      String key = keys.nextElement();
      System.out.printf("%s=%s%n", key, bundle.getString(key));
    }
    keys = bundle.getKeys();
    String pattern = bundle.getString(keys.nextElement());
    System.out.println(MessageFormat.format(pattern, new Object[]{0}));
    System.out.println(MessageFormat.format(pattern, new Object[]{1}));
    System.out.println(MessageFormat.format(pattern, new Object[]{30}));
  }
}