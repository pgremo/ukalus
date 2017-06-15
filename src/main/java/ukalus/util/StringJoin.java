/*
 * Created on Mar 19, 2005
 *
 */
package ukalus.util;


public class StringJoin implements Closure<String, Object> {

  private static final long serialVersionUID = 3257290218770806582L;
  private String delimiter;
  private StringBuffer buffer;

  public StringJoin(String delimiter, StringBuffer buffer) {
    this.delimiter = delimiter;
    this.buffer = buffer;
  }

  public Object apply(String item) {
    if (buffer.length() > 0) {
      buffer.append(delimiter);
    }
    buffer.append(item);
    return null;
  }

}
