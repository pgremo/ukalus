/*
 * Created on May 13, 2004
 *  
 */
package ironfist.persistence;

import java.io.IOException;

/**
 * Maintain an ordered list of objects, with the ability to execute a command
 * over all of them.
 * 
 * @author gremopm
 * @see Command
 */
public interface Log extends Iterable<Command> {

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
  void add(Command o) throws IOException;

  /**
   * Close this Log.
   * 
   * @throws Exception
   */
  void close() throws IOException;
}