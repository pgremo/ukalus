/*
 * Created on Feb 25, 2005
 *
 */
package ironfist.next.items;

import java.util.Random;

/**
 * @author gremopm
 *  
 */
public class RandomNameFactory extends RandomLabelFactory {

  public RandomNameFactory(Random random, String fileName, int maxSyllables) {
    super(random, fileName, maxSyllables);
  }

  public Object generate(Object argument) {
    String result = (String) super.generate(argument);
    return result.substring(0, 1)
      .toUpperCase() + result.substring(1);
  }
}