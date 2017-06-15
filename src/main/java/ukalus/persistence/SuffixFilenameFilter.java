package ukalus.persistence;

import java.io.File;
import java.io.FilenameFilter;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class SuffixFilenameFilter implements FilenameFilter {

  private String suffix;

  /**
   * Creates a new SuffixFilenameFilter object.
   * 
   * @param suffix
   *          DOCUMENT ME!
   */
  public SuffixFilenameFilter(String suffix) {
    this.suffix = suffix;
  }

  /**
   * @see java.io.FilenameFilter#accept(File, String)
   */
  public boolean accept(File dir, String name) {
    return name.endsWith(suffix);
  }
}