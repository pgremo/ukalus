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
public class ByteTransformer implements Transformer<String, Byte> {

  public Byte transform(String value) {
    return Byte.decode(value);
  }

}