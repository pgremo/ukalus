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
public class DoubleTransformer implements Closure<String, Double> {

  public Double apply(String value) {
    return Double.valueOf(value);
  }

}