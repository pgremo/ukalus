/*
 * Created on Jan 26, 2005
 *
 */
package ironfist.container.transformers;

import ironfist.container.Transformer;

/**
 * @author gremopm
 * 
 */
public class CharTransformer implements Transformer<String, Character> {

  public Character transform(String value) {
    return new Character((char) Integer.parseInt(value));
  }

}