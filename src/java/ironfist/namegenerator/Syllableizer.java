package ironfist.namegenerator;

import java.util.Arrays;

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

  public static String[] split(String word) {
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

}