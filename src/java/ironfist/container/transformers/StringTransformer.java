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
public class StringTransformer implements Transformer<String, String> {

  public String transform(String value) {
    return value;
  }

}