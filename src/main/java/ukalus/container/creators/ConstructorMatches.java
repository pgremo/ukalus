/**
 * 
 */
package ukalus.container.creators;

import ukalus.util.Closure;

import java.lang.reflect.Constructor;

public class ConstructorMatches implements Closure<Constructor, Boolean> {

  private static final long serialVersionUID = 3258132448905736759L;
  private int length;

  public ConstructorMatches(int length) {
    this.length = length;
  }

  public Boolean apply(Constructor item) {
    return item.getParameterTypes().length == length;
  }
}