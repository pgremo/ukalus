/*
 * Created on Feb 25, 2005
 *
 */
package ironfist.items;

import ironfist.util.Closure;

import java.util.Random;

class RandomWord implements Closure<Object, String> {

  private static final long serialVersionUID = 3258407339714425905L;
  private Random random;
  private String[] items;

  public RandomWord(Random random, String[] items) {
    this.random = random;
    this.items = items;
  }

  public String apply(Object argument) {
    return items[random.nextInt(items.length)];
  }
}