/*
 * Created on Jan 26, 2005
 *
 */
package ukalus.container.transformers;

import java.util.function.Function;

import java.io.File;

/**
 * @author gremopm
 * 
 */
public class FileConverter implements Function<String, File> {

  private static final long serialVersionUID = 3834308432025957939L;

  public File apply(String value) {
    return new File(value);
  }

}