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
import java.util.Random;

class RandomLabelFactory implements Factory {

  private Random random;
  private MarkovChain chains;
  private int minSyllables;
  private int maxSyllables;

  public RandomLabelFactory(Random random, String fileName, int minSyllables,
      int maxSyllables) {
    this.random = random;
    chains = new MarkovChain();

    try {
      Reader reader = new BufferedReader(new InputStreamReader(
        getClass().getResourceAsStream(fileName)));
      StreamTokenizer tokenizer = new StreamTokenizer(reader);

      while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
        if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
          String[] syllables = Strings.split(tokenizer.sval.toLowerCase());
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
    this.minSyllables = minSyllables;
    this.maxSyllables = maxSyllables;
  }

  public Object generate(Object argument) {
    int maxWords = ((Integer) argument).intValue();

    StringBuffer result = new StringBuffer();
    int wordCount = random.nextInt(maxWords) + 1;

    for (int index = 0; index < wordCount; index++) {
      if (index > 0) {
        result.append(" ");
      }

      StringBuffer word = new StringBuffer();
      int max = minSyllables + random.nextInt(maxSyllables - minSyllables) + 1;
      int count = 0;
      do {
        Object key = chains.next(null, random.nextDouble());
        for (count = 0; count < max && key != null; count++) {
          word.append(key);
          key = chains.next(key, random.nextDouble());
        }
      } while (count < minSyllables);

      result.append(word.toString());
    }

    return result.toString();
  }
}