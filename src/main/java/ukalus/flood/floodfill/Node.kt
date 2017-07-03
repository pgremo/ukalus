package ukalus.flood.floodfill

import ukalus.level.Level
import ukalus.math.Vector2D

import java.util.ArrayList

class Node(private val level: Level<Any>, val location: Vector2D, val distance: Int) {

    val children: Array<Node>
        get() {
            return DIRECTIONS
                    .map { location.plus(it) }
                    .filter { level.contains(it) && level[it] == FLOOR}
                    .mapTo(ArrayList<Node>(4)) { Node(level, it, distance + 1) }
                    .toTypedArray()
        }

    override fun equals(other: Any?) = this === other || other != null && other is Node && other.location == location

    override fun hashCode() = location.hashCode()

    override fun toString() = "$location=$distance"

    companion object {
        private val DIRECTIONS = arrayOf(Vector2D(1, 1), Vector2D(1, 0), Vector2D(1, -1), Vector2D(0, 1), Vector2D(0, -1), Vector2D(-1, 1), Vector2D(-1, 0), Vector2D(-1, -1))
        val FLOOR = Any()
        val WALL: Any = Any()
    }

}
