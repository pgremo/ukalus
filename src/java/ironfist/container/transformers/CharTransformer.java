/*
 * Created on Jan 26, 2005
 *
 */
package ironfist.container.transformers;

import ironfist.util.Closure;

/**
 * @author gremopm
 * 
 */
public class CharTransformer implements Closure<String, Character> {

  public Character apply(String value) {
    return new Character((char) Integer.parseInt(value));
  }

}