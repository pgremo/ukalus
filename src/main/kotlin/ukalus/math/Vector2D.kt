package ukalus.math

import java.io.ObjectStreamException
import java.io.Serializable
import java.lang.Math.sqrt

data class Vector2D(val x: Int, val y: Int) : Serializable {

    operator fun plus(value: Vector2D): Vector2D {
        return Vector2D(x + value.x, y + value.y)
    }

    operator fun minus(value: Vector2D): Vector2D {
        return Vector2D(x - value.x, y - value.y)
    }

    operator fun times(value: Double): Vector2D {
        return Vector2D((x * value).toInt(), (y * value).toInt())
    }

    operator fun div(value: Double): Vector2D {
        return Vector2D((x / value).toInt(), (y / value).toInt())
    }

    fun distance(destination: Vector2D): Double {
        return minus(destination).magnitude()
    }

    fun magnitude(): Double {
        return sqrt((x * x + y * y).toDouble())
    }

    fun rotate(vector: Vector2D): Vector2D {
        return Vector2D(x * vector.x - y * vector.y, x * vector.y + y * vector.x)
    }

    fun normal(): Vector2D {
        return div(magnitude())
    }

    fun orthogonal(): Vector2D {
        return Vector2D(y, -x)
    }

    @Throws(ObjectStreamException::class)
    internal fun writeReplace(): Any {
        return SerializedForm(x, y)
    }

    private class SerializedForm(private val x: Int, private val y: Int) : Serializable {

        @Throws(ObjectStreamException::class)
        internal fun readResolve(): Any {
            return Vector2D(x, y)
        }

        companion object {

            private const val serialVersionUID = 3257848779250939185L
        }

    }
}
