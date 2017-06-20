/*
 * Created on Jan 26, 2005
 *
 */
package ukalus.container.transformers;

import java.util.function.Function;

/**
 * @author gremopm
 * 
 */
public class FloatTransformer implements Function<String, Float> {

  private static final long serialVersionUID = 4049638988350436657L;

  public Float apply(String value) {
    return Float.valueOf(value);
  }

}