/*
 * Created on May 13, 2004
 *  
 */
package ironfist.persistence.file;

import ironfist.persistence.Store;

import java.io.File;
import java.io.Serializable;

import junit.framework.TestCase;

/**
 * @author gremopm
 * 
 */
public class FileStoreTest extends TestCase {

  private File file;

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    file = new File("test");
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    file.delete();
  }

  public void testOperations() throws Exception {
    Serializable original = "This is a test";
    Store storage = new FileStore(file);
    storage.store(original);
    Object result = storage.load();
    assertEquals(original, result);
  }
}