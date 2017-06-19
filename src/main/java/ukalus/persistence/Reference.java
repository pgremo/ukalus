/*
 * Created on May 13, 2004
 *
 */
package ukalus.persistence;

import java.io.Serializable;

/**
 * Generic reference object.
 * 
 * @author gremopm
 * 
 */
public class Reference<T extends Serializable> {

  private T object;

  /**
   * Get the current value of the reference.
   * 
   * @return
   */
  public T get() {
    return object;
  }

  /**
   * Set an object to reference.
   * 
   * @param object
   */
  public void set(T object) {
    this.object = object;
  }
}