/*
 * Created on Feb 25, 2005
 *  
 */
package ironfist.items;

import ironfist.util.Closure;
import ironfist.util.Loop;
import ironfist.util.MarkovChain;
import ironfist.util.StringJoin;
import ironfist.util.Tokens;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class RandomLabel implements Closure<Object, String> {

  private static final long serialVersionUID = 4121134723239457591L;
  private MarkovChain<String> chains;
  private Random random;
  private int minSyllables;
  private int maxSyllables;

  public RandomLabel(Random random, String fileName, int minSyllables,
      int maxSyllables) {
    this.random = random;
    this.minSyllables = minSyllables;
    this.maxSyllables = maxSyllables;
    this.chains = new MarkovChain<String>(random);

    Reader reader = new BufferedReader(new InputStreamReader(
        getClass().getResourceAsStream(fileName)));
    new Loop<String>(new Tokens(reader)).forEach(new SyllableLoader(chains));
  }

  public String apply(Object argument) {
    StringBuffer result = new StringBuffer();
    int wordMax = random.nextInt((Integer) argument) + 1;

    for (int index = 0; index < wordMax; index++) {
      if (index > 0) {
        result.append(" ");
      }

      List<String> syllables = new LinkedList<String>();
      int max = minSyllables + random.nextInt(maxSyllables - minSyllables);
      do {
        syllables.clear();
        Iterator<String> iterator = chains.iterator();
        while (syllables.size() <= max && iterator.hasNext()) {
          syllables.add(iterator.next());
        }
      } while (syllables.size() < minSyllables);

      new Loop<String>(syllables).forEach(new StringJoin("", result));
    }
    return result.toString();
  }
}