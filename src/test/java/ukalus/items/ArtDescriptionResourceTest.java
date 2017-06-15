/*
 * Created on Feb 25, 2005
 *
 */
package ukalus.items;

import static java.lang.System.out;

import java.util.ResourceBundle;

import junit.framework.TestCase;

/**
 * @author gremopm
 * 
 */
public class ArtDescriptionResourceTest extends TestCase {

  public void testGenerate() throws Exception {
    out.println(String.valueOf(ResourceBundle.getBundle(ArtDescriptionResource.class.getName())));
  }

}