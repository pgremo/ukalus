/*
 * Created on Feb 25, 2005
 *
 */
package ironfist.next.items;

import java.util.Enumeration;
import java.util.ResourceBundle;

import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class ArtDescriptionResourceTest extends TestCase {

  public void testGenerate() throws Exception {
    ResourceBundle bundle = ResourceBundle.getBundle(ArtDescriptionResource.class.getName());
    Enumeration keys = bundle.getKeys();

    while (keys.hasMoreElements()) {
      System.out.println(bundle.getString((String) keys.nextElement()));
    }
  }

}