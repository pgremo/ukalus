package ironfist.util;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class SyllablesTest extends TestCase {

  public void testSplitComplexWord() {
    String[] expected = new String[] { "he", "gan", "shab", "but", "tesh" };
    String[] actual = new Syllables("heganshabbuttesh").toArray();
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitSingleConsonant() {
    String[] expected = new String[] { "me", "ku" };
    String[] actual = new Syllables("meku").toArray();
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitMultipleSingleConsonants() {
    String[] expected = new String[] { "ma", "sa", "tei", "pe" };
    String[] actual = new Syllables("masateipe").toArray();
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitDoubleConsonantsAtEnd() {
    String[] expected = new String[] { "matt" };
    String[] actual = new Syllables("matt").toArray();
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitDoubleConsonants() {
    String[] expected = new String[] { "ob", "re", "gon" };
    String[] actual = new Syllables("obregon").toArray();
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitTripleConsonants() {
    String[] expected = new String[] { "bol", "chun" };
    String[] actual = new Syllables("bolchun").toArray();
    assertTrue(Arrays.equals(expected, actual));

    expected = new String[] { "bol", "vlun" };
    actual = new Syllables("bolvlun").toArray();
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitQuadrupleConsonants() {
    String[] expected = new String[] { "kalt", "glon" };
    String[] actual = new Syllables("kaltglon").toArray();
    assertTrue(Arrays.equals(expected, actual));
  }
}