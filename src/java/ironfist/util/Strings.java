/*
 * Created on Feb 26, 2005
 *
 */
package ironfist.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * @author pmgremo
 *  
 */
public final class Strings {

  private Strings() {

  }

  public static String join(Collection<String> parts, String delimiter) {
    StringBuffer result = new StringBuffer();
    boolean first = true;
    Iterator iterator = parts.iterator();
    while (iterator.hasNext()) {
      if (first) {
        first = false;
      } else {
        result.append(delimiter);
      }
      result.append(iterator.next());
    }
    return result.toString();
  }
  private static Pattern cleanPattern = Pattern.compile("\\W+",
    Pattern.CASE_INSENSITIVE);
  private static Pattern oneConsonant = Pattern.compile(
    "(?=[aeiou](?:tch|ch|ph|sh|th|wh|zh|[a-z&&[^aeiou]])[aeiou])([aeiou])(tch|ch|ph|sh|th|wh|zh|[a-z&&[^aeiou]])",
    Pattern.CASE_INSENSITIVE);
  private static Pattern twoConsonants = Pattern.compile(
    "(?=[aeiou][a-z&&[^aeiou]][a-z&&[^aeiou]][aeiou])([aeiou][a-z&&[^aeiou]])([a-z&&[^aeiou]])",
    Pattern.CASE_INSENSITIVE);
  private static Pattern threeConsonants = Pattern.compile(
    "(?=[aeiou][a-z&&[^aeiou]]{3}[aeiou])([aeiou](tch|ch|ph|sh|th|wh|zh|[a-z&&[^aeiou]]))([a-z&&[^aeiou]]{1,2})",
    Pattern.CASE_INSENSITIVE);
  private static Pattern fourConsonants = Pattern.compile(
    "(?=[aeiou][a-z&&[^aeiou]]{4}[aeiou])([aeiou][a-z&&[^aeiou]]{2})([a-z&&[^aeiou]]{2})",
    Pattern.CASE_INSENSITIVE);
  private static Pattern splitPattern = Pattern.compile("-+",
    Pattern.CASE_INSENSITIVE);

  /**
   * 1. Divide between two middle consonants. Split up words that have two
   * middle consonants. For example: hap/pen, bas/ket, let/ter, sup/per,
   * din/ner, and Den/nis. The only exceptions are the consonant digraphs. Never
   * split up consonant digraphs as they really represent only one sound. The
   * exceptions are "ch", "ph", "sh", "tch", "th", "wh" and "zh".
   * 
   * 2. Divide before a single middle consonant. When there is only one
   * syllable, you usually divide in front of it, as in: "o/pen", "i/tem",
   * "e/vil", and "re/port".
   * 
   * @author gremopm
   *  
   */
  public static String[] split(String word) {
    String result = cleanPattern.matcher(word)
      .replaceAll("-");
    result = oneConsonant.matcher(result)
      .replaceAll("$1-$2");
    result = twoConsonants.matcher(result)
      .replaceAll("$1-$2");
    result = threeConsonants.matcher(result)
      .replaceAll("$1-$3");
    result = fourConsonants.matcher(result)
      .replaceAll("$1-$2");
    return splitPattern.split(result);
  }

}