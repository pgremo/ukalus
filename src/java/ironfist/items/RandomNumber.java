/*
 * Created on Feb 25, 2005
 *  
 */
package ironfist.items;

import ironfist.util.Closure;

import java.util.Random;

class RandomNumber implements Closure<Object, String> {

  private static final long serialVersionUID = 3546075878359904568L;
  private String[] names;
  private Random random;
  private int min;
  private int max;

  public RandomNumber(Random random, int min, int max, String[] names) {
    this.random = random;
    this.min = min;
    this.max = max;
    this.names = names;
  }

  public String apply(Object argument) {
    String result = null;
    int value = random.nextInt(max - min) + min;
    if (names != null && value < names.length) {
      result = names[value];
    } else {
      result = String.valueOf(value);
    }
    return result;
  }
}