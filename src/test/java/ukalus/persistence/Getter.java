/*
 * Created on May 18, 2004
 *  
 */
package ukalus.persistence;

import ukalus.util.Closure;

/**
 * @author gremopm
 * 
 */
public class Getter implements Closure<Reference, Object> {

  private static final long serialVersionUID = 3257283617423241785L;

  public Object apply(Reference object) {
    return object.get();
  }

}