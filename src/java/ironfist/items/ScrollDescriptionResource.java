package ironfist.items;

import ironfist.util.MersenneTwister;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ListResourceBundle;
import java.util.Random;
import java.util.Set;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class ScrollDescriptionResource extends ListResourceBundle {

  private static final String FILE_NAME = "/wordlists/latin.txt";
  private static final int MAX_LABELS = 10;
  private static final int MAX_SYLLABLES = 3;

  protected Object[][] getContents() {
    Random random = new MersenneTwister();
    Set<Object> labels = new HashSet<Object>();

    Factory factory = new RandomLabelFactory(random, FILE_NAME, 1,
      MAX_SYLLABLES);
    while (labels.size() < MAX_LABELS) {
      String label = factory.generate(new Integer(3));

      labels.add(label.toString()
        .toUpperCase());
    }

    int count = 0;
    Object[][] result = new Object[labels.size()][2];
    Iterator iterator = labels.iterator();
    while (iterator.hasNext()) {
      result[count][0] = "scroll.description." + count;
      result[count][1] = "{0,choice,-1#scroll|1#a scroll|1<{0,number,integer} scrolls} labeled \""
          + iterator.next() + "\"";
      count++;
    }

    return result;
  }

}