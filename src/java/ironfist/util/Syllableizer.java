package ironfist.util;

import java.util.regex.Pattern;

public class Syllableizer {

  private static Pattern cleanPattern = Pattern.compile("\\W+",
    Pattern.CASE_INSENSITIVE);
  private static Pattern doubleConsonants = Pattern.compile(
    "(?!ch|ph|sh|tch|th|wh|zh)([a-z&&[^aeiouy]])([a-z&&[^aeiouy]])",
    Pattern.CASE_INSENSITIVE);
  private static Pattern loneConsonant = Pattern.compile(
    "([aeiouy])([a-z&&[^aeiouy]][aeiouy])", Pattern.CASE_INSENSITIVE);

  public static String[] split(String word) {
    String result = cleanPattern.matcher(word)
      .replaceAll("-");
    result = doubleConsonants.matcher(result)
      .replaceAll("$1-$2");
    result = loneConsonant.matcher(result)
      .replaceAll("$1-$2");
    return result.split("-");
  }

}