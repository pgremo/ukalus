package io;

import java.io.IOException;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class FileBlockTooShortException extends IOException {

  /**
   * Constructor for FileBlockTooShortException.
   */
  public FileBlockTooShortException() {
    super();
  }

  /**
   * Constructor for FileBlockTooShortException.
   * 
   * @param s
   */
  public FileBlockTooShortException(String s) {
    super(s);
  }
}