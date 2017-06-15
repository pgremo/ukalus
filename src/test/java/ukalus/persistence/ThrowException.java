/*
 * Created on Dec 28, 2004
 *
 */
package ukalus.persistence;

import ukalus.util.Closure;

/**
 * @author pmgremo
 * 
 */
public class ThrowException implements Closure<Reference, Object> {

  private static final long serialVersionUID = 3690754012043620921L;

  public Object apply(Reference object) {
    throw new RuntimeException();
  }

}