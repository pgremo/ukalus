/*
 * Created on Feb 25, 2005
 *
  */
package ironfist.next.items;

import java.util.Random;


class RandomWordFactory implements Factory {

  private Random random;

  private String[] items;

  public RandomWordFactory(Random random, String[] items) {
    this.random = random;
    this.items = items;
  }

  public Object generate(Object argument) {
    return items[random.nextInt(items.length)];
  }
}