package ironfist.persistence;

import java.io.IOException;

/**
 * @author pmgremo
 *  
 */
public class PersistenceException extends IOException {

  /**
   * Constructor for PersistenceException.
   */
  public PersistenceException() {
    super();
  }

  /**
   * Constructor for PersistenceException.
   * 
   * @param s
   */
  public PersistenceException(String s) {
    super(s);
  }

}