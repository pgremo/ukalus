/**
 * 
 */
package ironfist.container;

import java.lang.reflect.Constructor;

final class ConstructorParameterLengthMatches implements Predicate<Constructor> {

  private Object[] arguments;

  public ConstructorParameterLengthMatches(Object[] arguments) {
    this.arguments = arguments;
  }

  public boolean invoke(Constructor item) {
    return item.getParameterTypes().length == arguments.length;
  }
}