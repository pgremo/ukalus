/*
 * Created on Jan 26, 2005
 *
 */
package ironfist.container.transformers;

import ironfist.container.Transformer;

/**
 * @author gremopm
 * 
 */
public class BooleanTransformer implements Transformer<String, Boolean> {

  private Object value;

  public Boolean transform(String value) {
    return Boolean.valueOf(value);
  }

}