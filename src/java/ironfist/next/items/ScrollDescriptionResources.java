package ironfist.next.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import random.MersenneTwister;
import syllableizer.Syllableizer;


/**
 * DOCUMENT ME!
 *
 * @author pmgremo
 */
public class ScrollDescriptionResources extends ResourceBundle {
  private static final String FILE_NAME = "data/wordlists/latin.txt";
  private static final int MAX_LABELS = 10;
  private static final int MAX_WORDS = 3;
  private static final int MAX_SYLLABLES = 3;
  private boolean isLoaded;
  private Map contents;

  {
    contents = new HashMap();
  }

  private Map getContents() {
    if (!isLoaded) {
      synchronized (contents) {
        if (!isLoaded) {
          Random random = new MersenneTwister();
          Syllableizer gen = new Syllableizer();
          gen.setRandom(random);

          try {
            Reader reader = new BufferedReader(new FileReader(FILE_NAME));
            StreamTokenizer tokenizer = new StreamTokenizer(reader);

            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
              if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                gen.add(tokenizer.sval);
              }
            }
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          Set labels = new HashSet();

          while (labels.size() < MAX_LABELS) {
            String label = "";
            int count = random.nextInt(MAX_WORDS) + 1;

            for (int index = 0; index < count; index++) {
              if (index > 0) {
                label += " ";
              }

              String[] syllables = gen.getSyllables(random.nextInt(
                    MAX_SYLLABLES) + 1);

              for (int syllable = 0; syllable < syllables.length; syllable++) {
                label += syllables[syllable];
              }
            }

            labels.add(label.toUpperCase());
          }

          Iterator iterator = labels.iterator();

          while (iterator.hasNext()) {
            contents.put("scroll.description." + contents.size(),
              "{0,choice,-1#scroll|1#a scroll|1<{0,number,integer} scrolls} labeled \"" +
              iterator.next() + "\"");
          }

          isLoaded = true;
        }
      }
    }

    return contents;
  }

  /**
   * @see java.util.ResourceBundle#getKeys()
   */
  public Enumeration getKeys() {
    return Collections.enumeration(getContents().keySet());
  }

  /**
   * @see java.util.ResourceBundle#handleGetObject(String)
   */
  protected Object handleGetObject(String key) {
    return getContents()
             .get(key);
  }
}
