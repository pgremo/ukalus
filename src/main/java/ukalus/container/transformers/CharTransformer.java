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
public class CharTransformer implements Closure<String, Character> {

  private static final long serialVersionUID = 4048795684356370743L;

  public Character apply(String value) {
    return new Character((char) Integer.parseInt(value));
  }

}