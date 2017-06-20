/**
 * 
 */
package ukalus.container.creators;

import java.lang.reflect.Constructor;
import java.util.function.Predicate;

public class ConstructorMatches implements Predicate<Constructor> {

  private int length;

  public ConstructorMatches(int length) {
    this.length = length;
  }

  public boolean test(Constructor item) {
    return item.getParameterTypes().length == length;
  }
}