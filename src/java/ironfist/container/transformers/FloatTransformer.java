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
public class FloatTransformer implements Closure<String, Float> {

  public Float apply(String value) {
    return Float.valueOf(value);
  }

}