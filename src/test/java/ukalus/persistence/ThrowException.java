/*
 * Created on Dec 28, 2004
 *
 */
package ukalus.persistence;

import java.util.function.Function;

/**
 * @author pmgremo
 * 
 */
public class ThrowException implements Function<Reference, Object> {

  private static final long serialVersionUID = 3690754012043620921L;

  public Object apply(Reference object) {
    throw new RuntimeException();
  }

}