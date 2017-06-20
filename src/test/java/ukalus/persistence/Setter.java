/*
 * Created on May 18, 2004
 *  
 */
package ukalus.persistence;

import java.util.function.Function;

import java.io.Serializable;

/**
 * @author gremopm
 * 
 */
public class Setter<T extends Serializable> implements Function<Reference<T>, Object> {

  private static final long serialVersionUID = 3256442491011217201L;
  private T value = null;

  public Setter(T value) {
    this.value = value;
  }

  public Object apply(Reference<T> object) {
    object.set(value);
    return value;
  }

}