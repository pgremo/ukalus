package ukalus.util;

import java.util.Random;

import junit.framework.TestCase;

public class MersenneTwisterTest extends TestCase {

  private static final int SESSIONS = 100;
  private static final int FLIPS = 1000000;

  public void testEquidistribution() {
    Random random = new MersenneTwister();

    double total = 0;
    for (int i = 0; i < SESSIONS; i++) {
      double trues = 0;
      for (int j = 0; j < FLIPS; j++) {
        if (random.nextBoolean()) {
          trues++;
        }
      }
      total += trues / FLIPS;
    }
    total /= SESSIONS;
    System.out.println(total);
    assertEquals((double).5, total, .0001);
  }

}
