/*
 * Created on Feb 16, 2005
 *
 */
package ukalus.level

import ukalus.math.Vector2D
import java.io.Serializable

/**
 * @author gremopm
 */
class Level<T>(private val data: Array<Array<T>>) : Serializable {

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

    override fun toString(): String {
        val result = StringBuilder()
        for (x in 0..length - 1) {
            result.append((0..width - 1)
                    .map { y -> data[x][y] as Int }
                    .map {
                        when {
                            it == 100 -> "+"
                            it > 0 -> " "
                            else -> "#"
                        }
                    }
                    .joinToString("", "", "\n"))
        }

        return result.toString()
    }

    companion object {
        private const val serialVersionUID = 3617578201795014705L
    }

}