/*
 * Created on Feb 25, 2005
 *
 */
package ironfist.items;

import java.util.Random;

/**
 * @author gremopm
 * 
 */
public class RandomName extends RandomLabel {

  private static final long serialVersionUID = 3258416131630183993L;

  public RandomName(Random random, String fileName, int minSyllables,
      int maxSyllables) {
    super(random, fileName, minSyllables, maxSyllables);
  }

  public String apply(Object argument) {
    StringBuffer result = new StringBuffer(super.apply(argument));
    result.setCharAt(0, Character.toUpperCase(result.charAt(0)));
    return result.toString();
  }
}