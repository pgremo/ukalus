package ironfist.util;

import java.util.regex.Pattern;

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