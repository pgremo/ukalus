/*
 * Created on Feb 25, 2005
 *
 */
package ironfist.next.items;

import java.util.ResourceBundle;

import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class ArtDescriptionResourceTest extends TestCase {

  public void testGenerate() throws Exception {
    System.out.println(String.valueOf(ResourceBundle.getBundle(ArtDescriptionResource.class.getName())));
  }

}