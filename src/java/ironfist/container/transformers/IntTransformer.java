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

  public Integer apply(String value) {
    return Integer.decode(value);
  }

}