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
public class IntTransformer implements Transformer<String, Integer> {

  public Integer transform(String value) {
    return Integer.decode(value);
  }

}