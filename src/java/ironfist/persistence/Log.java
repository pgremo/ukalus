/*
 * Created on May 13, 2004
 *  
 */
package ironfist.persistence;

import java.io.IOException;
import java.util.Iterator;

/**
 * Maintain an ordered list of objects, with the ability to execute a command
 * over all of them.
 * 
 * @author gremopm
 * @see Command
 */
public interface Log {

  /**
   * Remove all objects that have been added to this Log.
   * 
   * @throws Exception
   */
  void clear() throws IOException;

  /**
   * Add the given object to this Log.
   * 
   * @param object
   *          to be added to this Log
   * @throws Exception
   */
  void add(Object object) throws IOException;

  /**
   * Return an iterator over objects in this Log.
   * 
   * @param command
   *          to use to process objects
   * @throws Exception
   */
  Iterator iterator() throws IOException, ClassNotFoundException;

  void close() throws IOException;
}