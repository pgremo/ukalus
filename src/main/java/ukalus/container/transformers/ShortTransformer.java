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
public class ShortTransformer implements Function<String, Short> {

  private static final long serialVersionUID = 3256439222473667638L;

  public Short apply(String value) {
    return Short.valueOf(value);
  }

}