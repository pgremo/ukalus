package ironfist.util;

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
public class SringsTest extends TestCase {

  public void testCommutative() {
    MarkovChain chains = new MarkovChain();

    String[] syllables = Strings.split("PROPTERFUISSEDEM");
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

    String[] actual = Strings.split(result.toString());
    assertTrue(Arrays.equals(expected.toArray(), actual));
  }

  public void testSplit1() {
    String[] expected = new String[]{"he", "gan", "shab", "but", "tesh"};
    String[] actual = Strings.split("heganshabbuttesh");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplit2() {
    String[] expected = new String[]{"tee", "ten", "te"};
    String[] actual = Strings.split("teetente");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplit3() {
    String[] expected = new String[]{"me", "ku"};
    String[] actual = Strings.split("meku");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplit4() {
    String[] expected = new String[]{"ma", "sa", "tei", "pe"};
    String[] actual = Strings.split("masateipe");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplit5() {
    String[] expected = new String[]{"matt"};
    String[] actual = Strings.split("matt");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplit6() {
    String[] expected = new String[]{"ob", "re", "gon"};
    String[] actual = Strings.split("obregon");
    assertTrue(Arrays.equals(expected, actual));
  }
}