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
public class IntTransformer implements Function<String, Integer> {

  private static final long serialVersionUID = 3257845485112145200L;

  public Integer apply(String value) {
    return Integer.valueOf(value);
  }

}