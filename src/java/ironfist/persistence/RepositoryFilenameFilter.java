package ironfist.persistence;

import java.io.File;
import java.io.FilenameFilter;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class RepositoryFilenameFilter implements FilenameFilter {

  private String suffix;

  /**
   * Creates a new RepositoryFileFilter object.
   * 
   * @param suffix
   *          DOCUMENT ME!
   */
  public RepositoryFilenameFilter(String suffix) {
    this.suffix = suffix;
  }

  /**
   * @see java.io.FilenameFilter#accept(File, String)
   */
  public boolean accept(File dir, String name) {
    return name.endsWith(suffix);
  }
}