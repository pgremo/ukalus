package ukalus.items;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;
import ukalus.util.Closure;

import java.util.HashSet;
import java.util.ListResourceBundle;
import java.util.Random;
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
    Random random = new RandomAdaptor(new MersenneTwister());
    Set<Object> labels = new HashSet<Object>();

    Closure<Object, String> factory = new RandomLabel(random, FILE_NAME, 1,
      MAX_SYLLABLES);
    while (labels.size() < MAX_LABELS) {
      String label = factory.apply(new Integer(3))
        .toString()
        .toUpperCase();

      labels.add(label);
    }

    Object[][] result = new Object[MAX_LABELS][];
    Set<Object> contents = new HashSet<Object>();
    while (contents.size() < MAX_LABELS) {
      String current = factory.apply(new Integer(3))
        .toString()
        .toUpperCase();
      if (contents.add(current)) {
        int index = contents.size() - 1;
        result[index] = new Object[]{
            PREFIX + index,
            new StringBuffer(
              "{0,choice,-1#scroll|1#a scroll|1<{0,number,integer} scrolls} labeled \"").append(
              current)
              .append("\"")
              .toString()};
      }
    }

    return result;
  }

}