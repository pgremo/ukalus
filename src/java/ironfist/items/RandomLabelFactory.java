/*
 * Created on Feb 25, 2005
 *  
 */
package ironfist.items;

import ironfist.util.MarkovChain;
import ironfist.util.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class RandomLabelFactory implements Factory {

  private MarkovChain<String> chains;
  private Random random;
  private int minSyllables;
  private int maxSyllables;

  public RandomLabelFactory(Random random, String fileName, int minSyllables,
      int maxSyllables) {
    this.random = random;
    this.minSyllables = minSyllables;
    this.maxSyllables = maxSyllables;
    this.chains = new MarkovChain<String>(random);

    try {
      Reader reader = new BufferedReader(new InputStreamReader(
        getClass().getResourceAsStream(fileName)));
      StreamTokenizer tokenizer = new StreamTokenizer(reader);
      while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
        if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
          String[] syllables = Strings.split(tokenizer.sval.toLowerCase());
          String key = null;
          for (String item : syllables) {
            chains.add(key, item);
            key = item;
          }
          chains.add(key, null);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String generate(Object argument) {
    int maxWords = ((Integer) argument).intValue();

    StringBuffer result = new StringBuffer();
    int wordCount = random.nextInt(maxWords) + 1;

    for (int index = 0; index < wordCount; index++) {
      if (index > 0) {
        result.append(" ");
      }

      List<String> syllables = new LinkedList<String>();
      int max = minSyllables + random.nextInt(maxSyllables - minSyllables);
      do {
        syllables.clear();
        Iterator<String> iterator = chains.iterator();
        while (syllables.size() <= max && iterator.hasNext()) {
          String key = iterator.next();
          syllables.add(key);
        }
      } while (syllables.size() < minSyllables);

      result.append(Strings.join(syllables, ""));
    }

    return result.toString();
  }
}