package ukalus

import java.util.function.Predicate

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class StairsPredicate : Predicate<Tile?> {

    /**
     * Returns the direction.

     * @return String
     */
    /**
     * Sets the direction.

     * @param direction
     * *          The direction to set
     */
    var direction: String? = null

    /**
     * @see com.threat.game.Predicate.invoke
     */
    override fun test(value: Tile?): Boolean {
        var result = false
        if (value != null) {
            val type = value.tileType

            if (type is Floor) {
                val portal = type.portal

                if (portal is Stairs) {
                    if (portal.direction == direction) {
                        result = true
                    }
                }
            }
        }

        return result
    }
}