package ironfist.persistence.test;

import ironfist.persistence.Persistence;
import junit.framework.TestCase;

/**
 * @author pmgremo
 *
 */
public class PersistenceTest extends TestCase {

	/**
	 * Constructor for PersistenceTest.
	 * @param arg0
	 */
	public PersistenceTest(String arg0) {
		super(arg0);
	}
	
	public void testOperations() throws Exception{
		Persistence.create("test");
		String key = "key";
		String value = "value";
		Persistence.put(key, value);
		assertEquals(value, Persistence.get(key));
		Persistence.delete("test");
	}
	
}
