/*
 * Created on Aug 19, 2004
 *
 */
package ironfist.util;

/**
 * @author gremopm
 *  
 */
public class Counter implements Cloneable {

  private int count;

  public Counter() {
    this(0);
  }

  public Counter(int count) {
    this.count = count;
  }

  public Counter increment() {
    count++;
    return this;
  }

  public Counter decrement() {
    count--;
    return this;
  }

  public int getCount() {
    return count;
  }

  public Object clone() {
    return new Counter(count);
  }

}