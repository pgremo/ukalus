/*
 * Created on May 18, 2004
 *  
 */
package ironfist.persistence;

/**
 * @author gremopm
 * 
 */
public class Getter implements Command {

  private static final long serialVersionUID = 3257283617423241785L;

  public Object execute(Reference object) {
    return object.get();
  }

}