package ironfist.util;

import java.util.regex.Pattern;

/**
 * 1. Divide between two middle consonants. Split up words that have two middle
 * consonants. For example: hap/pen, bas/ket, let/ter, sup/per, din/ner, and
 * Den/nis. The only exceptions are the consonant digraphs. Never split up
 * consonant digraphs as they really represent only one sound. The exceptions
 * are "ch", "ph", "sh", "tch", "th", "wh" and "zh".
 * 
 * 2. Divide before a single middle consonant. When there is only one syllable,
 * you usually divide in front of it, as in: "o/pen", "i/tem", "e/vil", and
 * "re/port".
 * 
 * @author gremopm
 *  
 */
public class Syllableizer {

  private static Pattern cleanPattern = Pattern.compile("\\W+",
    Pattern.CASE_INSENSITIVE);
  private static Pattern doubleConsonants = Pattern.compile(
    "(?!ch|ph|sh|tch|th|wh|zh)([a-z&&[^aeiou]])([a-z&&[^aeiou]])",
    Pattern.CASE_INSENSITIVE);
  private static Pattern loneConsonant = Pattern.compile(
    "([aeiou])([a-z&&[^aeiou]][aeiou])", Pattern.CASE_INSENSITIVE);
  private static Pattern splitPattern = Pattern.compile("-+",
    Pattern.CASE_INSENSITIVE);

  public static String[] split(String word) {
    String result = cleanPattern.matcher(word)
      .replaceAll("-");
    result = doubleConsonants.matcher(result)
      .replaceAll("$1-$2");
    result = loneConsonant.matcher(result)
      .replaceAll("$1-$2");
    result = loneConsonant.matcher(result)
      .replaceAll("$1-$2");
    return splitPattern.split(result);
  }

}