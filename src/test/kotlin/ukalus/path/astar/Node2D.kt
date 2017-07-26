/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar

import ukalus.level.Level
import ukalus.math.Vector2D

/**
 * @author gremopm
 */
class Node2D(private val level: Level<*>, val location: Vector2D, override val parent: Node2D?) : Node {
    override var cost: Double = 0.0
    override var estimate: Double = 0.0

    override val successors: Collection<Node>
        get() = DIRECTIONS
                .map { location + it }
                .filter { (parent == null || it != parent.location) && level.contains(it) }
                .map { Node2D(level, it, this) }

    override val total
        get() = cost + estimate

    override fun equals(other: Any?) = other == null || other is Node2D && other.location == location

    override fun hashCode() = location.hashCode()

    override fun toString() = location.toString()

    companion object {
        private val DIRECTIONS = arrayOf(Vector2D(1, 0), Vector2D(0, 1), Vector2D(-1, 0), Vector2D(0, -1))
    }
}