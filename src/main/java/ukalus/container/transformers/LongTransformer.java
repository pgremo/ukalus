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
public class LongTransformer implements Function<String, Long> {

  private static final long serialVersionUID = 3832616287942883636L;

  public Long apply(String value) {
    return Long.valueOf(value);
  }

}