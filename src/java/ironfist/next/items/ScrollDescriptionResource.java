package ironfist.next.items;

import ironfist.util.MarkovChain;
import ironfist.util.MersenneTwister;
import ironfist.util.Syllableizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
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

  private static final String FILE_NAME = "/wordlists/ancientwyrms.txt";
  private static final int MAX_LABELS = 10;
  private static final int MAX_WORDS = 3;
  private static final int MAX_SYLLABLES = 3;

  protected Object[][] getContents() {
    MarkovChain chains = new MarkovChain();
    try {
      Reader reader = new BufferedReader(new InputStreamReader(
        getClass().getResourceAsStream(FILE_NAME)));
      StreamTokenizer tokenizer = new StreamTokenizer(reader);

      while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
        if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
          String[] syllables = Syllableizer.split(tokenizer.sval);
          String key = syllables[0];

          for (int i = 1; i < syllables.length; i++) {
            chains.add(key, syllables[i]);
            key = syllables[i];
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Random random = new MersenneTwister();
    Set labels = new HashSet();

    while (labels.size() < MAX_LABELS) {
      StringBuffer label = new StringBuffer();
      int count = random.nextInt(MAX_WORDS) + 1;

      for (int index = 0; index < count; index++) {
        if (index > 0) {
          label.append(" ");
        }

        StringBuffer word = new StringBuffer();
        Object key = chains.next(null, random.nextDouble());

        for (int syllableCount = random.nextInt(MAX_SYLLABLES) + 1; syllableCount > 0
            && key != null; syllableCount--) {
          word.append(key);
          key = chains.next(key, random.nextDouble());
        }

        label.append(word.toString());

      }

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