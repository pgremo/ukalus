/*
 * Created on Dec 28, 2004
 *
 */
package ironfist.persistence;

/**
 * @author pmgremo
 * 
 */
public class ThrowException implements Command {

  private static final long serialVersionUID = 3690754012043620921L;

  public Object execute(Reference object) {
    throw new RuntimeException();
  }

}