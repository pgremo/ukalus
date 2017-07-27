package ukalus

import java.util.Random

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Roll {

    private var randomizer: Random? = null
    private var canFail: Boolean = false
    private var canTriumph: Boolean = false

    init {
        randomizer = Random()
        canFail = true
        canTriumph = true
    }

    /**
     * DOCUMENT ME!

     * @param value
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    fun roll(value: Int): Int {
        var total = value
        var direction = 1
        var roll = (randomizer!!.nextInt(10) + 1) * direction

        if (roll == 1 && canFail) {
            direction = -1
            roll = (randomizer!!.nextInt(10) + 1) * direction
        }

        total += roll

        while (roll == 10 && canTriumph) {
            roll = (randomizer!!.nextInt(10) + 1) * direction
            total += roll
        }

        return total
    }
}