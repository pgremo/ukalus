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
public class BooleanTransformer implements Closure<String, Boolean> {

  private static final long serialVersionUID = 3688509878813667384L;

  public Boolean apply(String value) {
    return Boolean.valueOf(value);
  }

}