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

  private static final long serialVersionUID = 3832616287942883636L;

  public Long apply(String value) {
    return Long.decode(value);
  }

}