package ukalus.persistence;

import junit.framework.TestCase;

/**
 * @author pmgremo
 *  
 */
public class PersistenceTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
    Persistence.create("test");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    Persistence.close();
    Persistence.delete("test");
  }

  public void testOperations() throws Exception {
    String key = "key";
    String value = "value";
    Persistence.put(key, value);
    assertEquals(value, Persistence.get(key));
  }

}