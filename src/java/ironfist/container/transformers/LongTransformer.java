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
public class LongTransformer implements Closure<String, Long> {

  public Long apply(String value) {
    return Long.decode(value);
  }

}