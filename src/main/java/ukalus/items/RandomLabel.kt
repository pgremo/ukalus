/*
 * Created on Feb 25, 2005
 *  
 */
package ukalus.items

import ukalus.util.MarkovChain
import ukalus.util.Syllables
import java.util.*
import java.util.function.Function

open class RandomLabel(private val random: Random,
                       fileName: String,
                       private val minSyllables: Int,
                       private val maxSyllables: Int) : Function<Any, String> {

    private val chains: MarkovChain<String> by lazy {
        MarkovChain<String>(random).apply {
            javaClass.getResourceAsStream(fileName).reader().use { stream ->
                stream.readLines().map(String::toLowerCase).map(::Syllables).forEach(this::addAll)
            }
        }
    }

    override fun apply(argument: Any): String {
        return generateSequence { chains.take(random.nextInt(minSyllables, maxSyllables + 1)).map { it } }
                .filter { it.size >= minSyllables }
                .take(random.nextInt(argument as Int) + 1)
                .map { it.joinToString("") }
                .joinToString(" ")
    }
}
