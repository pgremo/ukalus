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
public class FloatTransformer implements Transformer<String, Float> {

  public Float transform(String value) {
    return Float.valueOf(value);
  }

}