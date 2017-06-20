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
public class DoubleTransformer implements Function<String, Double> {

  private static final long serialVersionUID = 3618416042523702834L;

  public Double apply(String value) {
    return Double.valueOf(value);
  }

}