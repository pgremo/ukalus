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
public class Reference {

  private Serializable object;

  /**
   * Get the current value of the reference.
   * 
   * @return
   */
  public Serializable get() {
    return object;
  }

  /**
   * Set an object to reference.
   * 
   * @param object
   */
  public void set(Serializable object) {
    this.object = object;
  }
}