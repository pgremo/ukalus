/*
 * Created on Jan 26, 2005
 *
 */
package ironfist.container.transformers;

import ironfist.util.Closure;

import java.io.File;

/**
 * @author gremopm
 * 
 */
public class FileConverter implements Closure<String, File> {

  public File apply(String value) {
    return new File(value);
  }

}