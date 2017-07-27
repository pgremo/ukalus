package ukalus

import java.io.Serializable
import java.util.ArrayList

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Floor : TileType, Serializable {
    /**
     * Returns the door.

     * @return Door
     */
    /**
     * Sets the door.

     * @param value
     * *          The door to set
     */
    var door: Door? = null
    /**
     * Returns the creature.

     * @return Creature
     */
    /**
     * Sets the creature.

     * @param creature
     * *          The creature to set
     */
    var creature: Creature? = null
    private var things: MutableCollection<Thing>? = null
    /**
     * Returns the connector.

     * @return Connector
     */
    /**
     * Sets the connector.

     * @param value
     * *          The connector to set
     */
    var portal: Portal? = null

    init {
        things = ArrayList<Thing>()
    }

    /**
     * Adds a thing

     * @param value
     * *          The thing to add
     */
    fun addThing(value: Thing) {
        things!!.add(value)
    }

    /**
     * Removes a thing

     * @param value
     * *          The thing to remove
     */
    fun removeThing(value: Thing) {
        things!!.remove(value)
    }

    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    fun getThings(): Iterator<Thing> {
        return things!!.iterator()
    }

    /**
     * DOCUMENT ME!

     * @return DOCUMENT ME!
     */
    val thingCount: Int
        get() = things!!.size

    override fun toString(): String {
        return "Floor"
    }

    companion object {

        private const val serialVersionUID = 3546927995609625650L
    }
}