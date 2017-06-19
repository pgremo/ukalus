/*
 * Created on Feb 25, 2005
 *  
 */
package ukalus.items;

import ukalus.util.Closure;

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
    int value = random.nextInt(max - min) + min;
    return names != null && value < names.length ? names[value] : String.valueOf(value);
  }
}