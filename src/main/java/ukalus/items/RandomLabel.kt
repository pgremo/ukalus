/*
 * Created on Feb 25, 2005
 *  
 */
package ukalus.items

import ukalus.util.*
import java.util.*
import java.util.function.Function

open class RandomLabel(private val random: Random, fileName: String, private val minSyllables: Int,
                       private val maxSyllables: Int) : Function<Any, String> {
    private val chains: MarkovChain<String> = MarkovChain(random)

    init {
        javaClass.getResourceAsStream(fileName).reader().use {
            Tokens(it).forEach { chains.addAll(Syllables(it.toLowerCase())) }
        }
    }

    override fun apply(argument: Any): String {
        val result = StringBuilder()
        val wordMax = random.nextInt(argument as Int) + 1

        for (index in 0..wordMax - 1) {
            if (index > 0) {
                result.append(" ")
            }

            val syllables = LinkedList<String>()
            val max = minSyllables + random.nextInt(maxSyllables - minSyllables)
            do {
                syllables.clear()
                val iterator = chains.iterator()
                while (syllables.size <= max && iterator.hasNext()) {
                    syllables.add(iterator.next())
                }
            } while (syllables.size < minSyllables)

            syllables.joinTo(result, "")
        }
        return result.toString()
    }
}