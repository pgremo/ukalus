/*
 * Created on Feb 16, 2005
 *
 */
package ukalus.level

/**
 * @author gremopm
 */
class IntLevel(private val data: Array<Array<Int>>) : Level<Int>(data) {
    override fun toString() = StringBuilder().apply {
        for (i in data.indices) {
            for (j in data[i].indices) {
                append(data[i][j].let {
                    when {
                        it == 100 -> '+'
                        it > 0 -> ' '
                        else -> '#'
                    }
                })
            }
            append("\n")
        }
    }.toString()
}