/*
 * Created on Feb 25, 2005
 *  
 */
package ukalus.items

import ukalus.util.MarkovChain
import ukalus.util.nextInt
import ukalus.util.syllables
import java.util.*
import java.util.function.Function

open class RandomLabel(private val random: Random,
                       fileName: String,
                       private val minSyllables: Int,
                       private val maxSyllables: Int) : Function<Any, String> {

    private val chains: MarkovChain<String> by lazy {
        MarkovChain<String>().apply {
            javaClass.getResourceAsStream(fileName).reader().use { stream ->
                stream.readLines().map(String::toLowerCase).map(::syllables).forEach(this::addAll)
            }
        }
    }

    override fun apply(argument: Any): String {
        return generateSequence { chains.randomWalk(random).take(random.nextInt(minSyllables, maxSyllables + 1)).map { it } }
                .filter { it.count() >= minSyllables }
                .take(random.nextInt(argument as Int) + 1)
                .map { it.joinToString("") }
                .joinToString(" ")
    }
}
