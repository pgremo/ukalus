/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence;

import java.io.IOException;
import java.io.Serializable;

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
  void store(Serializable object) throws IOException;

  /**
   * Load the previously stored object.
   * 
   * @return the previously stored object.
   * @throws Exception
   */
  Serializable load() throws IOException, ClassNotFoundException;

  /**
   * Close this Store.
   * 
   * @throws IOException
   */
  void close() throws IOException;
}