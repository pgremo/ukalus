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

  private static final long serialVersionUID = 3256439222473667638L;

  public Short apply(String value) {
    return Short.decode(value);
  }

}