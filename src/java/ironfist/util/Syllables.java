/*
 * Created on Feb 26, 2005
 *
 */
package ironfist.util;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author pmgremo
 * 
 */
public class Syllables extends AbstractList<String> {
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

  private List<String> syllables;

  public Syllables(String value) {
    String result = cleanPattern.matcher(value)
      .replaceAll("-");
    result = oneConsonant.matcher(result)
      .replaceAll("$1-$2");
    result = twoConsonants.matcher(result)
      .replaceAll("$1-$2");
    result = threeConsonants.matcher(result)
      .replaceAll("$1-$3");
    result = fourConsonants.matcher(result)
      .replaceAll("$1-$2");
    syllables = Arrays.asList(splitPattern.split(result));
  }

  public Iterator<String> iterator() {
    return syllables.iterator();
  }

  public int size() {
    return syllables.size();
  }

  public String get(int i) {
    return syllables.get(i);
  }

}