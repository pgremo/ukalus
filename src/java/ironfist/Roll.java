package ironfist;

import java.util.Random;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Roll {

  private Random randomizer;
  private boolean canFail;
  private boolean canTriumph;

  {
    randomizer = new Random();
    canFail = true;
    canTriumph = true;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int roll(int value) {
    int total = value;
    int direction = 1;
    int roll = (randomizer.nextInt(10) + 1) * direction;

    if (roll == 1 && canFail) {
      direction = -1;
      roll = (randomizer.nextInt(10) + 1) * direction;
    }

    total += roll;

    while (roll == 10 && canTriumph) {
      roll = (randomizer.nextInt(10) + 1) * direction;
      total += roll;
    }

    return total;
  }
}