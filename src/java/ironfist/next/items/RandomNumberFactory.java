/*
 * Created on Feb 25, 2005
 *
  */
package ironfist.next.items;

import java.util.Random;


class RandomNumberFactory implements Factory {

  private String[] names;

  private Random random;

  private int min;

  private int max;

  public RandomNumberFactory(Random random, int min, int max, String[] names) {
    this.random = random;
    this.min = min;
    this.max = max;
    this.names = names;
  }

  public Object generate(Object argument) {
    int value = random.nextInt(max - min) + min;
    if (names != null && value < names.length) {
      return names[value];
    }
    return String.valueOf(value);
  }
}