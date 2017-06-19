package ukalus.items;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;
import ukalus.util.Closure;

import java.util.HashSet;
import java.util.ListResourceBundle;
import java.util.Set;

/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class ScrollDescriptionResource extends ListResourceBundle {

  private static final String PREFIX = "scroll.description.";
  private static final String FILE_NAME = "/ukalus/wordlists/latin.txt";
  private static final int MAX_LABELS = 10;
  private static final int MAX_SYLLABLES = 3;

  protected Object[][] getContents() {
    Closure<Object, String> factory = new RandomLabel(new RandomAdaptor(new MersenneTwister()), FILE_NAME, 1, MAX_SYLLABLES);

    Object[][] result = new Object[MAX_LABELS][];
    Set<Object> contents = new HashSet<>();
    while (contents.size() < MAX_LABELS) {
      String current = factory.apply(3).toUpperCase();
      if (contents.add(current)) {
        int index = contents.size() - 1;
        result[index] = new Object[]{
          PREFIX + index,
          String.format("{0,choice,-1#scroll|1#a scroll|1<{0,number,integer} scrolls} labeled \"%s\"", current)
        };
      }
    }

    return result;
  }

}