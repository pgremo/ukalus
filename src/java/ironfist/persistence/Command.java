/*
 * Created on Mar 9, 2004
 *  
 */
package ironfist.persistence;

import java.io.Serializable;

/**
 * Encapsulation of a method/function.
 * 
 * @author gremopm
 *  
 */
public interface Command extends Serializable {

  /**
   * Execute this command on the given object returning a result.
   * 
   * @param reference
   *          argument for this command.
   * @return result.
   */
  Object execute(Reference reference);
}