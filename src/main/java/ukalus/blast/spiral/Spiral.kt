package ukalus.blast.spiral

import ukalus.blast.Blast
import ukalus.math.Vector2D
import java.lang.Math.PI
import java.lang.Math.atan2
import java.util.function.Function
import kotlin.coroutines.experimental.buildSequence

class Spiral : Blast {

    private var scanner: Function<Vector2D, Boolean>? = null
    private var origin: Vector2D? = null
    private var radius: Int = 0

    private val result: MutableSet<Vector2D> = mutableSetOf()

    override fun getTemplate(location: Vector2D, scanner: Function<Vector2D, Boolean>, radius: Int): Set<Vector2D> {

        if (radius < 1) {
            throw IllegalArgumentException()
        }

        this.scanner = scanner
        this.origin = location
        this.radius = radius

        result.add(location)

        scanCircle(1, 0.0, 359.9)

        return result
    }

    internal fun scanCircle(r: Int, th1: Double, th2: Double) {
        var thl = th1
        val circle = CIRCLES[r]!!
        var wasBlocked = false
        var isClear = false
        for (i in circle.indices) {
            val arcPoint = circle[i]

            if (arcPoint.theta != thl && arcPoint.theta != th2) {
                if (arcPoint.leading > th2 || arcPoint.lagging < thl) {
                    continue
                }
            }

            val point = origin!!.plus(Vector2D(arcPoint.x, arcPoint.y))
            result.add(point)
            val isBlocked = scanner!!.apply(point)

            if (isBlocked) {
                if (wasBlocked) {
                    continue
                } else if (isClear) {
                    if (r < radius) {
                        scanCircle(r + 1, thl, arcPoint.leading)
                    }
                } else {
                    if (arcPoint.theta == 0.0) {
                        thl = 0.0
                    } else {
                        thl = arcPoint.leading
                    }
                }
            } else {
                isClear = true
                if (wasBlocked) {
                    thl = circle[i - 1].lagging
                } else {
                    wasBlocked = false
                    continue
                }
            }
            wasBlocked = isBlocked
        }

        if (!wasBlocked && r < radius) {
            scanCircle(r + 1, thl, th2)
        }
    }

    private class ArcPoint internal constructor(internal var x: Int, internal var y: Int) : Comparable<ArcPoint> {
        internal var theta: Double = 0.0
        internal var leading: Double = 0.0
        internal var lagging: Double = 0.0

        override fun toString(): String {
            return "($x,$y)=[theta=$theta,leading=$leading,lagging=$lagging]"
        }

        internal fun angle(y: Double, x: Double): Double {
            var a1 = atan2(y, x)
            if (a1 < 0) {
                a1 += 2 * PI
            }
            var a2 = 360.0 - a1 * 180.0 / PI
            if (a2 == 360.0) {
                a2 = 0.0
            }
            return a2
        }

        init {
            theta = angle(y.toDouble(), x.toDouble())
            if (x < 0 && y < 0) {
                // top left
                leading = angle(y - 0.5, x + 0.5)
                lagging = angle(y + 0.5, x - 0.5)
            } else if (x < 0) {
                // top right
                leading = angle(y - 0.5, x - 0.5)
                lagging = angle(y + 0.5, x + 0.5)
            } else if (y > 0) {
                // bottom right
                leading = angle(y + 0.5, x - 0.5)
                lagging = angle(y - 0.5, x + 0.5)
            } else {
                // bottom left
                leading = angle(y + 0.5, x + 0.5)
                lagging = angle(y - 0.5, x - 0.5)
            }
        }

        override fun compareTo(other: ArcPoint): Int {
            return if (theta > other.theta) 1 else -1
        }

        override fun equals(other: Any?): Boolean {
            return theta == (other as ArcPoint).theta
        }

        override fun hashCode(): Int {
            return x * y
        }
    }

    companion object {

        private val CIRCLES: Map<Int, List<ArcPoint>>

        init {
            val origin = Vector2D(0, 0)

            CIRCLES = buildSequence {
                for (i in -20..20) {
                    for (j in -20..20) {
                        yield(Vector2D(i, j))
                    }
                }
            }
                    .groupBy { (origin.distance(it) + 0.5).toInt() }
                    .mapValues { (_, items) -> items.map { (x, y) -> ArcPoint(x, y) }.sorted() }
        }
    }

}
