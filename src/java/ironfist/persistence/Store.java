/*
 * Created on May 13, 2004
 *  
 */
package ironfist.persistence;

import java.io.IOException;

/**
 * Encapsulation of the storing and loading of an object.
 * 
 * @author gremopm
 *  
 */
public interface Store {

  /**
   * Store the given object (eg, serialization).
   * 
   * @param object
   * @throws Exception
   */
  void store(Object object) throws IOException;

  /**
   * Load the previously stored object.
   * 
   * @return the previously stored object.
   * @throws Exception
   */
  Object load() throws IOException, ClassNotFoundException;
  
  void close() throws IOException;
}