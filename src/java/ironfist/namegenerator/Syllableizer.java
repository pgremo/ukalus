package ironfist.namegenerator;

import ironfist.util.MarkovChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Syllableizer {

  private static final char SEPERATOR = '-';
  private static final String CONSONANTS = "bcdfghjklmnpqrstvwxz";
  private static final String VOWELS = "aeiouy";
  private static final String[] DIPGRAPHS = {
      "ch",
      "ph",
      "sh",
      "tch",
      "th",
      "wh",
      "zh"};
  private MarkovChain dictionary = new MarkovChain();
  private Random random;

  public String[] split(String word) {
    String splitted = "";
    word = word.replaceAll("\\W+", "" + SEPERATOR);

    // split word at double-consonants
    char previous = word.charAt(0);
    String gather = "" + previous;

    for (int i = 1; i < word.length(); i++) {
      char current = word.charAt(i);

      if ((CONSONANTS.indexOf(current) != -1)
          && (CONSONANTS.indexOf(previous) != -1)
          && (Arrays.binarySearch(DIPGRAPHS, "" + previous + current) < 0)
          && ((gather.length() > 1) || (VOWELS.indexOf(gather.charAt(0)) != -1))) {
        splitted = splitted + gather + SEPERATOR;
        gather = "";
      }

      gather = gather + current;
      previous = current;
    }

    splitted = splitted + gather;

    // divide before a single middle consonant
    word = splitted;
    previous = word.charAt(0);
    splitted = "" + previous;

    for (int i = 1; i < (word.length() - 1); i++) {
      char current = word.charAt(i);
      char next = word.charAt(i + 1);

      if ((current != SEPERATOR)
          && ((CONSONANTS.indexOf(current) != -1)
              && (CONSONANTS.indexOf(previous) == -1) && (CONSONANTS.indexOf(next) == -1))
          && ((splitted.length() == 0) || (word.charAt(i + 1) != SEPERATOR))) {
        splitted = splitted + SEPERATOR;
      }

      splitted = splitted + current;
      previous = current;
    }

    splitted = splitted + word.charAt(word.length() - 1);

    return splitted.split(SEPERATOR + "+");
  }

  public void add(String word) {
    String[] syllables = split(word.toLowerCase());
    String key = syllables[0];

    for (int i = 1; i < syllables.length; i++) {
      dictionary.add(key, syllables[i]);
      key = syllables[i];
    }
  }

  public String[] getSyllables(int size) {
    List result = new ArrayList(size);
    Object key = dictionary.next(null, random.nextDouble());

    while ((result.size() <= size) && (key != null)) {
      result.add(key);
      key = dictionary.next(key, random.nextDouble());
    }

    return (String[]) result.toArray(new String[result.size()]);
  }

  /**
   * Returns the random.
   * 
   * @return Random
   */
  public Random getRandom() {
    return random;
  }

  /**
   * Sets the random.
   * 
   * @param random
   *          The random to set
   */
  public void setRandom(Random random) {
    this.random = random;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return dictionary.toString();
  }
}