package ukalus.persistence;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author pmgremo
 *  
 */
public class PersistenceTest  {

  @Before
  public void setUp() throws Exception {
    Persistence.INSTANCE.create("test");
  }

  @After
  public void tearDown() throws Exception {
    Persistence.INSTANCE.close();
    Persistence.INSTANCE.delete("test");
  }

  @Test
  public void testOperations() throws Exception {
    String key = "key";
    String value = "value";
    Persistence.INSTANCE.put(key, value);
    assertEquals(value, Persistence.INSTANCE.get(key));
  }

}