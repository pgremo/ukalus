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
public class ShortTransformer implements Transformer<String, Short> {

  public Short transform(String value) {
    return Short.decode(value);
  }

}