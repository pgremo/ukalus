/*
 * Created on Jan 26, 2005
 *
 */
package ukalus.container.transformers;

import ukalus.util.Closure;

/**
 * @author gremopm
 * 
 */
public class StringTransformer implements Closure<String, String> {

  private static final long serialVersionUID = 3258133535582925616L;

  public String apply(String value) {
    return value;
  }

}