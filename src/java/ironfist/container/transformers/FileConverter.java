/*
 * Created on Jan 26, 2005
 *
 */
package ironfist.container.transformers;

import ironfist.container.Transformer;

import java.io.File;

/**
 * @author gremopm
 * 
 */
public class FileConverter implements Transformer<String, File> {

  public File transform(String value) {
    return new File(value);
  }

}