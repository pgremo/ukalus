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
public class ShortTransformer implements Closure<String, Short> {

  public Short apply(String value) {
    return Short.decode(value);
  }

}