/*
 * Created on Jan 26, 2005
 *
 */
package ukalus.container.transformers;

import java.util.function.Function;

/**
 * @author gremopm
 * 
 */
public class ByteTransformer implements Function<String, Byte> {

  private static final long serialVersionUID = 3257571723862684980L;

  public Byte apply(String value) {
    return Byte.valueOf(value);
  }

}