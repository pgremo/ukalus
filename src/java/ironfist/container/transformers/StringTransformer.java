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
public class StringTransformer implements Closure<String, String> {

  public String apply(String value) {
    return value;
  }

}