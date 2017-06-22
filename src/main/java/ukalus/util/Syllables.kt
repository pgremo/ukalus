/*
 * Created on Feb 26, 2005
 *
 */
package ukalus.util

import kotlin.text.RegexOption.IGNORE_CASE

private val splitPattern = Regex("-+", IGNORE_CASE)

private val patterns = listOf(
        Pair(Regex("\\W+", IGNORE_CASE), "-"),
        Pair(Regex("(?=[aeiou](?:tch|ch|ph|sh|th|wh|zh|[a-z&&[^aeiou]])[aeiou])([aeiou])(tch|ch|ph|sh|th|wh|zh|[a-z&&[^aeiou]])", IGNORE_CASE), "$1-$2"),
        Pair(Regex("(?=[aeiou][a-z&&[^aeiou]][a-z&&[^aeiou]][aeiou])([aeiou][a-z&&[^aeiou]])([a-z&&[^aeiou]])", IGNORE_CASE), "$1-$2"),
        Pair(Regex("(?=[aeiou][a-z&&[^aeiou]]{3}[aeiou])([aeiou](tch|ch|ph|sh|th|wh|zh|[a-z&&[^aeiou]]))([a-z&&[^aeiou]]{1,2})", IGNORE_CASE), "$1-$3"),
        Pair(Regex("(?=[aeiou][a-z&&[^aeiou]]{4}[aeiou])([aeiou][a-z&&[^aeiou]]{2})([a-z&&[^aeiou]]{2})", IGNORE_CASE), "$1-$2")
)

fun syllables(value: String): List<String> {
    return splitPattern.split(patterns.fold(value) { acc, (pattern, replacement) -> pattern.replace(acc, replacement) })
}