/*
 * Created on Mar 19, 2005
 *
 */
package ukalus.items

import ukalus.util.MarkovChain
import ukalus.util.Syllables
import java.util.function.Function

class SyllableLoader(private val chains: MarkovChain<String>) : Function<String, Any?> {

    override fun apply(item: String): Any? {
        chains.addAll(Syllables(item.toLowerCase()))
        return null
    }

    companion object {

        private val serialVersionUID = 3906648609259598642L
    }

}
