/*
 * Created on Feb 25, 2005
 *  
 */
package ironfist.next.items;

import ironfist.util.MarkovChain;
import ironfist.util.Syllableizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Random;

class RandomLabelFactory implements Factory {

  private Random random;

  private MarkovChain chains;

  private int maxSyllables;

  public RandomLabelFactory(Random random, String fileName, int maxSyllables) {
    this.random = random;
    chains = new MarkovChain();

    try {
      Reader reader = new BufferedReader(new InputStreamReader(
        getClass().getResourceAsStream(fileName)));
      StreamTokenizer tokenizer = new StreamTokenizer(reader);

      while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
        if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
          String[] syllables = Syllableizer.split(tokenizer.sval.toLowerCase());
          String key = null;

          for (int i = 0; i < syllables.length; i++) {
            chains.add(key, syllables[i]);
            key = syllables[i];
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

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
      Object key = chains.next(null, random.nextDouble());

      for (int syllableCount = random.nextInt(maxSyllables) + 1; syllableCount > 0
          && key != null; syllableCount--) {
        word.append(key);
        key = chains.next(key, random.nextDouble());
      }

      result.append(word.toString());
    }

    return result.toString();
  }
}