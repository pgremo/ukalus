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
public class LongTransformer implements Transformer<String, Long> {

  public Long transform(String value) {
    return Long.decode(value);
  }

}