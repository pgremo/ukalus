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

  private static final long serialVersionUID = 4049638988350436657L;

  public Float apply(String value) {
    return Float.valueOf(value);
  }

}