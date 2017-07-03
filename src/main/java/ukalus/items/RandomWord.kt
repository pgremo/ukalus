/*
 * Created on Feb 25, 2005
 *
 */
package ukalus.items

import ukalus.util.random
import java.util.Random
import java.util.function.Function

internal class RandomWord(private val random: Random, private val items: Array<String>) : Function<Any, String> {

    override fun apply(argument: Any) = items.random(random)!!
}