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

  public Byte apply(String value) {
    return Byte.decode(value);
  }

}