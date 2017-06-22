/*
 * Created on Feb 26, 2005
 *
 */
package ukalus.util

import java.util.regex.Pattern.CASE_INSENSITIVE
import java.util.regex.Pattern.compile

private val cleanPattern = compile("\\W+", CASE_INSENSITIVE)
private val oneConsonant = compile("(?=[aeiou](?:tch|ch|ph|sh|th|wh|zh|[a-z&&[^aeiou]])[aeiou])([aeiou])(tch|ch|ph|sh|th|wh|zh|[a-z&&[^aeiou]])", CASE_INSENSITIVE)
private val twoConsonants = compile("(?=[aeiou][a-z&&[^aeiou]][a-z&&[^aeiou]][aeiou])([aeiou][a-z&&[^aeiou]])([a-z&&[^aeiou]])", CASE_INSENSITIVE)
private val threeConsonants = compile("(?=[aeiou][a-z&&[^aeiou]]{3}[aeiou])([aeiou](tch|ch|ph|sh|th|wh|zh|[a-z&&[^aeiou]]))([a-z&&[^aeiou]]{1,2})", CASE_INSENSITIVE)
private val fourConsonants = compile("(?=[aeiou][a-z&&[^aeiou]]{4}[aeiou])([aeiou][a-z&&[^aeiou]]{2})([a-z&&[^aeiou]]{2})", CASE_INSENSITIVE)
private val splitPattern = compile("-+", CASE_INSENSITIVE)

fun syllables(value: String): List<String> {
    var result = cleanPattern.matcher(value).replaceAll("-")
    result = oneConsonant.matcher(result).replaceAll("$1-$2")
    result = twoConsonants.matcher(result).replaceAll("$1-$2")
    result = threeConsonants.matcher(result).replaceAll("$1-$3")
    result = fourConsonants.matcher(result).replaceAll("$1-$2")
    return mutableListOf(*splitPattern.split(result))
}