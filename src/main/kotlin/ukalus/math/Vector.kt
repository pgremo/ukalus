package ukalus.math

import java.io.Serializable

/**

 * @author pmgremo
 */
data class Vector
/**
 * Creates a new Coordinate object.

 * @param x
 * *          coordinate
 * *
 * @param y
 * *          coordinate
 */
(
        /**
         * return the x coordinate

         * @return x coordinate
         */
        val x: Double,
        /**
         * return the y coordinate

         * @return y coordinate
         */
        val y: Double) : Cloneable, Serializable {

    /**
     * add a vector to this and return the sum

     * @param value
     * *          vector to add
     * *
     * *
     * @return Vector that is the sum
     */
    fun add(value: Vector): Vector {
        return Vector(x + value.x, y + value.y)
    }

    /**
     * subtract a vector from this and return the difference

     * @param value
     * *          vector to subtract
     * *
     * *
     * @return Vector that is the difference
     */
    fun subtract(value: Vector): Vector {
        return Vector(x - value.x, y - value.y)
    }

    /**
     * multiply a vector to this and return the product

     * @param value
     * *          Vector to multiply
     * *
     * *
     * @return Vector that is the product
     */
    fun multiply(value: Double): Vector {
        return Vector(x * value, y * value)
    }

    /**
     * divide a vector by this and return the quotient

     * @param value
     * *          Vector to divide by
     * *
     * *
     * @return Vector that is the quotient
     */
    fun divide(value: Double): Vector {
        return Vector(x / value, y / value)
    }

    /**
     * return the length of this vector Math.sqrt((x * x) + (y * y))

     * @return length of this vector
     */
    fun magnitude(): Double {
        return Math.sqrt(x * x + y * y)
    }

    /**
     * calculate the distance from this vector to the given vector

     * @param destination
     * *
     * *
     * @return distance
     */
    fun distance(destination: Vector): Double {
        return subtract(destination).magnitude()
    }

    /**
     * calculate the vector normal for this vector divide(magnitude())

     * @return the vector normal
     */
    fun normal(): Vector {
        return divide(magnitude())
    }

    /**
     * calculate the dot product for the given vector and this (x * value.getX()) +
     * (y * value.getY())

     * @param value
     * *          to evaluate
     * *
     * *
     * @return dot product
     */
    fun dot(value: Vector): Double {
        return x * value.x + y * value.y
    }

    /**
     * Calculate the angle between a vector and this. Basically:
     * Math.acos(normal().dot(value.normal()))

     * @param value
     * *          other vector
     * *
     * *
     * @return angle
     */
    fun angle(value: Vector): Double {
        var result = normal().dot(value.normal())

        if (result < -1) {
            result = -1.0
        } else if (result > 1) {
            result = 1.0
        }

        return Math.acos(result)
    }

    /**
     * determine the vector of this rotated a given number of degrees

     * @param value
     * *          degrees to rotate
     * *
     * *
     * @return new vector
     */
    fun rotate(value: Double): Vector {
        val phi = Math.toRadians(value)
        val cos = Math.cos(phi)
        val sin = Math.sin(phi)
        return Vector(x * cos + y * sin, y * cos - x * sin)
    }

    /**
     * rotate a vector based on a given vector

     * @param vector
     * *          to rotate by
     * *
     * *
     * @return new vector
     */
    fun rotate(vector: Vector): Vector {
        return Vector(x * vector.x - y * vector.y,
                x * vector.y + y * vector.x)
    }

    /**
     * project this vector along given vector.

     * @param value
     * *          to project along
     * *
     * *
     * @return new vector
     */
    fun project(value: Vector): Vector {
        return multiply(dot(value) / dot(this))
    }

    /**
     * return a vector orthoganal to this one Vector(y, -x)

     * @return new vector
     */
    fun orthoganal(): Vector {
        return Vector(y, -x)
    }

    /**
     * @see java.lang.Object.equals
     */
    override fun equals(other: Any?): Boolean {
        return other != null && other is Vector
                && x == other.x && y == other.y
    }

    /**
     * @see java.lang.Object.clone
     */
    public override fun clone(): Any {
        return Vector(x, y)
    }

    /**
     * @see java.lang.Object.toString
     */
    override fun toString(): String {
        return "(x=$x,y=$y)"
    }

    /**
     * @see java.lang.Object.hashCode
     */
    override fun hashCode(): Int {
        var bits = java.lang.Double.doubleToLongBits(x)
        bits = bits xor java.lang.Double.doubleToLongBits(y) * 31

        return bits.toInt() xor (bits shr 32).toInt()
    }

    companion object {

        private const val serialVersionUID = 3258410646906155832L
    }
}