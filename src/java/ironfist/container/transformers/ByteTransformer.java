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
public class ByteTransformer implements Closure<String, Byte> {

  private static final long serialVersionUID = 3257571723862684980L;

  public Byte apply(String value) {
    return Byte.valueOf(value);
  }

}