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
    Syllables syllables = new Syllables("heganshabbuttesh");
    String[] actual = syllables.toArray(new String[syllables.size()]);
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitSingleConsonant() {
    String[] expected = new String[] { "me", "ku" };
    Syllables syllables = new Syllables("meku");
    String[] actual = syllables.toArray(new String[syllables.size()]);
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitMultipleSingleConsonants() {
    String[] expected = new String[] { "ma", "sa", "tei", "pe" };
    Syllables syllables = new Syllables("masateipe");
    String[] actual = syllables.toArray(new String[syllables.size()]);
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitDoubleConsonantsAtEnd() {
    String[] expected = new String[] { "matt" };
    Syllables syllables = new Syllables("matt");
    String[] actual = syllables.toArray(new String[syllables.size()]);
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitDoubleConsonants() {
    String[] expected = new String[] { "ob", "re", "gon" };
    Syllables syllables = new Syllables("obregon");
    String[] actual = syllables.toArray(new String[syllables.size()]);
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitTripleConsonants() {
    String[] expected = new String[] { "bol", "chun" };
    Syllables syllables = new Syllables("bolchun");
    String[] actual = syllables.toArray(new String[syllables.size()]);
    assertTrue(Arrays.equals(expected, actual));

    expected = new String[] { "bol", "vlun" };
    syllables = new Syllables("bolvlun");
    actual = syllables.toArray(new String[syllables.size()]);
    assertTrue(Arrays.equals(expected, actual));
  }

  public void testSplitQuadrupleConsonants() {
    String[] expected = new String[] { "kalt", "glon" };
    Syllables syllables = new Syllables("kaltglon");
    String[] actual = syllables.toArray(new String[syllables.size()]);
    assertTrue(Arrays.equals(expected, actual));
  }
}