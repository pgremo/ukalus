package ukalus

import ukalus.math.Vector2D

import java.io.Serializable

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class Tile(var location: Vector2D, var tileType: TileType) : Serializable {
    override fun toString(): String = "$location=$tileType"
}