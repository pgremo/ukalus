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
public class DoubleTransformer implements Transformer<String, Double> {

  public Double transform(String value) {
    return Double.valueOf(value);
  }

}