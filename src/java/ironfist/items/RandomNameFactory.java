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
public class RandomNameFactory extends RandomLabelFactory {

  public RandomNameFactory(Random random, String fileName, int minSyllables,
      int maxSyllables) {
    super(random, fileName, minSyllables, maxSyllables);
  }

  public Object generate(Object argument) {
    StringBuffer result = new StringBuffer((String) super.generate(argument));
    result.setCharAt(0, Character.toUpperCase(result.charAt(0)));
    return result.toString();
  }
}