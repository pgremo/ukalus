/*
 * Created on Feb 25, 2005
 *  
 */
package ukalus.items

import java.util.*
import java.util.function.Function

internal class RandomNumber(private val random: Random, private val min: Int, private val max: Int, private val names: Array<String>?) : Function<Any, String> {

    override fun apply(argument: Any): String {
        return (random.nextInt(min, max)).let {
            if (names != null && it < names.size) names[it] else it.toString()
        }
    }
}