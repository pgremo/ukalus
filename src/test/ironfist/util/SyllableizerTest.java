package ironfist.util;

import ironfist.util.MarkovChain;
import ironfist.util.MersenneTwister;
import ironfist.util.Syllableizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class SyllableizerTest extends TestCase {

  public void testCommutative() {
    MarkovChain chains = new MarkovChain();

    String[] syllables = Syllableizer.split("PROPTERFUISSEDEM".toLowerCase());
    Object key = syllables[0];

    for (int i = 1; i < syllables.length; i++) {
      chains.add(key, syllables[i]);
      key = syllables[i];
    }
    chains.add(syllables[syllables.length - 1], null);

    Random random = new MersenneTwister();
    Collection expected = new ArrayList();
    int syllableCount = random.nextInt(10) + 1;
    StringBuffer result = new StringBuffer();
    key = chains.next(null, random.nextDouble());

    while (syllableCount-- > -1 && key != null) {
      result.append(key);
      expected.add(key);
      key = chains.next(key, random.nextDouble());
    }

    String[] actual = Syllableizer.split(result.toString());
    assertTrue(Arrays.equals(expected.toArray(), actual));
  }

  public void testSplit() {
    String[] expected = new String[]{"He", "gan", "shab", "but", "tesh"};
    String[] actual = Syllableizer.split("Heganshabbuttesh");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplit2() {
    String[] expected = new String[]{"tee", "ten", "te"};
    String[] actual = Syllableizer.split("teetente");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplit3() {
    String[] expected = new String[]{"me", "ku"};
    String[] actual = Syllableizer.split("meku");
    assertTrue(Arrays.equals(expected, actual));
  }
  
  public void testSplit4() {
    String[] expected = new String[]{"ma", "sa", "tei", "pe"};
    String[] actual = Syllableizer.split("masateipe");
    assertTrue(Arrays.equals(expected, actual));
  }


}