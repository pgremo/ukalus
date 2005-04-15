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
public class IntTransformer implements Closure<String, Integer> {

  private static final long serialVersionUID = 3257845485112145200L;

  public Integer apply(String value) {
    return Integer.valueOf(value);
  }

}