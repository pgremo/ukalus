/*
 * Created on Feb 25, 2005
 *
 */
package ukalus.items

import java.util.Random

/**
 * @author gremopm
 */
class RandomName(random: Random, fileName: String, minSyllables: Int,
                 maxSyllables: Int) : RandomLabel(random, fileName, minSyllables, maxSyllables) {

    override fun apply(argument: Any): String {
        return super.apply(argument).capitalize()
    }
}