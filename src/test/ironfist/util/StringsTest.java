package ironfist.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class StringsTest extends TestCase {

  public void testCommutative() {
    Random random = new MersenneTwister();
    MarkovChain<String> chains = new MarkovChain<String>(random);

    String[] syllables = Strings.split("PROPTERFUISSEDEM");
    String key = null;
    for(String item: syllables){
      chains.add(key, item);
      key = item;
    }
    chains.add(syllables[syllables.length - 1], null);

    Collection<String> expected = new ArrayList<String>();
    int syllableCount = random.nextInt(10) + 1;
    StringBuffer result = new StringBuffer();
    Iterator<String> iterator = chains.iterator();
    while (syllableCount-- > -1 && iterator.hasNext()) {
      key = iterator.next();
      result.append(key);
      expected.add(key);
    }

    String[] actual = Strings.split(result.toString());
    assertTrue(Arrays.equals(expected.toArray(), actual));
  }

  public void testSplitComplexWord() {
    String[] expected = new String[]{"he", "gan", "shab", "but", "tesh"};
    String[] actual = Strings.split("heganshabbuttesh");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitSingleConsonant() {
    String[] expected = new String[]{"me", "ku"};
    String[] actual = Strings.split("meku");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitMultipleSingleConsonants() {
    String[] expected = new String[]{"ma", "sa", "tei", "pe"};
    String[] actual = Strings.split("masateipe");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitDoubleConsonantsAtEnd() {
    String[] expected = new String[]{"matt"};
    String[] actual = Strings.split("matt");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitDoubleConsonants() {
    String[] expected = new String[]{"ob", "re", "gon"};
    String[] actual = Strings.split("obregon");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitTripleConsonants() {
    String[] expected = new String[]{"bol", "chun"};
    String[] actual = Strings.split("bolchun");
    assertTrue(Arrays.equals(expected, actual));

    expected = new String[]{"bol", "vlun"};
    actual = Strings.split("bolvlun");
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitQuadrupleConsonants() {
    String[] expected = new String[]{"kalt", "glon"};
    String[] actual = Strings.split("kaltglon");
    assertTrue(Arrays.equals(expected, actual));
  }
}