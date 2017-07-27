package ukalus

import java.io.Serializable

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
open class Wall : TileType, Serializable {

    override fun toString(): String {
        return "Wall"
    }

    companion object {

        private const val serialVersionUID = 3544671793421168693L
    }

}