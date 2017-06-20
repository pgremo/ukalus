package ukalus.items

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import java.util.*

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class ScrollDescriptionResource : ListResourceBundle() {

    val factory = RandomLabel(RandomAdaptor(MersenneTwister()), "/ukalus/wordlists/latin.txt", 1, 3)

    override fun getContents(): Array<Array<Any>> {
        return generateSequence { factory.apply(3).toUpperCase() }
                .distinct()
                .take(10)
                .toList()
                .mapIndexed { index, current -> arrayOf<Any>("scroll.description.$index", "{0,choice,-1#scroll|1#a scroll|1<{0,number,integer} scrolls} labeled \"$current\"", current) }
                .toTypedArray()
    }
}