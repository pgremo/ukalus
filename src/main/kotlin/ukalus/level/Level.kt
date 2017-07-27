package ukalus.level

import ukalus.math.Vector2D
import java.io.Serializable


open class Level<T>(private val data: Array<Array<T>>) : Serializable {

    operator fun contains(location: Vector2D): Boolean = 0 <= location.x && location.x < data.size
            && 0 <= location.y
            && location.y < data[location.x].size

    operator fun get(location: Vector2D): T = data[location.x][location.y]

    operator fun set(location: Vector2D, value: T) {
        data[location.x][location.y] = value
    }

    val length: Int
        get() = data.size

    val width: Int
        get() = data[0].size
}