/*
 * Created on May 18, 2004
 *  
 */
package ukalus.persistence;

import ukalus.util.Closure;

import java.io.Serializable;

/**
 * @author gremopm
 * 
 */
public class Setter implements Closure<Reference, Object> {

  private static final long serialVersionUID = 3256442491011217201L;
  private Serializable value = null;

  public Setter(Serializable value) {
    this.value = value;
  }

  public Object apply(Reference object) {
    object.set(value);
    return value;
  }

}