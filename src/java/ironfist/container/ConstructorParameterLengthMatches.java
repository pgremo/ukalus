/**
 * 
 */
package ironfist.container;

import ironfist.util.Closure;

import java.lang.reflect.Constructor;

final class ConstructorParameterLengthMatches implements
    Closure<Constructor, Boolean> {

  private static final long serialVersionUID = 3258132448905736759L;
  private Object[] arguments;

  public ConstructorParameterLengthMatches(Object[] arguments) {
    this.arguments = arguments;
  }

  public Boolean apply(Constructor item) {
    return item.getParameterTypes().length == arguments.length;
  }
}