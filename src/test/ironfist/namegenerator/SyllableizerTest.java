package ironfist.namegenerator;

import ironfist.namegenerator.Syllableizer;
import ironfist.util.MersenneTwister;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class SyllableizerTest extends TestCase {

  /**
   * Constructor for SyllableSplitterTest.
   * 
   * @param arg0
   */
  public SyllableizerTest(String arg0) {
    super(arg0);
  }

  public void testSplit() {
    String[] control = new String[]{"He", "gan", "shab", "but", "t"};
    Syllableizer splitter = new Syllableizer();
    String[] syllables = splitter.split("Heganshabbutt");
    assertTrue(Arrays.equals(control, syllables));
  }

  public void testCommutative() {
    Syllableizer splitter = new Syllableizer();
    splitter.setRandom(new MersenneTwister());
    splitter.add("PROPTERFUISSEDEM");

    String[] control = splitter.getSyllables(10);
    String word = "";

    for (int index = 0; index < control.length; index++) {
      word += control[index];
    }

    String[] syllables = splitter.split(word);
    assertTrue(Arrays.equals(control, syllables));
  }
}