/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence.file;

import ukalus.persistence.Reference;
import java.util.function.Function;

/**
 * @author gremopm
 * 
 */
public class MockLogCommand implements Function<Reference, Object> {

  private static final long serialVersionUID = 3257570589924405561L;
  private String name;

  public MockLogCommand(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see persistence.Command#execute(java.lang.Object)
   */
  public Object apply(Reference object) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object obj) {
    return ((MockLogCommand) obj).name.equals(name);
  }
}