/*
 * Created on May 18, 2004
 *  
 */
package ironfist.persistence;

import java.io.Serializable;

/**
 * @author gremopm
 * 
 */
public class Setter implements Command {

  private static final long serialVersionUID = 3256442491011217201L;
  private Serializable value = null;

  public Setter(Serializable value) {
    this.value = value;
  }

  public Object execute(Reference object) {
    object.set(value);
    return value;
  }

}