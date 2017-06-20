/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence;

import java.util.function.Function;

import java.io.IOException;

/**
 * Maintain an ordered list of objects, with the ability to execute a command
 * over all of them.
 * 
 * @author gremopm
 * @see Function
 */
public interface Log extends Iterable<Function> {

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
  void add(Function o) throws IOException;

  /**
   * Close this Log.
   * 
   * @throws Exception
   */
  void close() throws IOException;
}