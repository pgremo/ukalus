/*
 * Created on May 13, 2004
 *
 */
package ironfist.persistence;

/**
 * Generic reference object.
 * 
 * @author gremopm
 *  
 */
public class Reference {

  private Object object;

  /**
   * Get the current value of the reference.
   * 
   * @return
   */
  public Object get() {
    return object;
  }

  /**
   * Set an object to reference.
   * 
   * @param object
   */
  public void set(Object object) {
    this.object = object;
  }
}