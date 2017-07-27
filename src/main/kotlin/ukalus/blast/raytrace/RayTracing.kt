package ukalus.blast.raytrace

import ukalus.blast.Blast
import ukalus.math.Vector2D
import java.util.*
import java.util.function.Function

class RayTracing : Blast {

    // the cell array
    private val cells: Array<Cell> = Array(MAX_LIGHT_RADIUS, { Cell() })

    // the 'circle' array. For any given row, we won't check higher than
    // this given cell.
    private var circle: IntArray? = null

    // current light radius
    private var radius: Int = 0
    lateinit private var result: MutableSet<Vector2D>
    private var origin: Vector2D? = null
    private var scanner: Function<Vector2D, Boolean>? = null

    private fun getUpper(x: Int, y: Int): Int {
        // got a blocker at row bX, cell bY. do all values
        // and scale by a factor of 10 for the integer math.
        var result = 10 * (10 * x - VIEW) / (10 * y + VIEW)
        // check upper bound for blocker on diagonal
        if (result < 10) {
            result = 10
        }

        return result
    }

    private fun getLower(x: Int, y: Int): Int {
        // got a blocker at row bX, cell bY. do all values
        // and scale by a factor of 10 for the integer math.
        var result = BIG_SHADOW
        if (y != 0) {
            result = 10 * (10 * x + VIEW) / (10 * y - VIEW)
        }

        return result
    }

    private fun scanOctant(octant: Int) {
        cells[0].init()

        var isAllDark = false
        var isCornerVisible = false

        for (row in 1..radius) {
            var isRowDark = true

            var top = circle!![row]
            if (top > row) {
                top = row
            }

            for (cell in 0..top) {
                // check for all_dark - we've finished the octant but
                // have yet to fill in '0' for the rest of the sight grid
                if (isAllDark) {
                    continue
                }

                // translate X,Y coordinate
                val location = origin!!.plus(ROW_TRANSFORM[octant].times(row.toDouble()))
                        .plus(CELL_TRANSFORM[octant].times(cell.toDouble()))

                val blocker = scanner!!.apply(location)

                var upInc = 10
                var lowInc = 10
                val previous = cell - 1

                // STEP 1 - inherit values from immediate West, if possible
                if (cell < row) {
                    // check for delayed lighting
                    if (cells[cell].isLitDelay) {
                        // blockers don't light up with lit_delay.
                        if (!blocker) {
                            if (cells[previous].isLit) {
                                if (cells[previous].lowMax != 0) {
                                    cells[cell].isLit = false
                                    // steal lower values
                                    cells[cell].lowMax = cells[previous].lowMax
                                    cells[cell].lowCount = cells[previous].lowCount
                                    cells[previous].lowCount = 0
                                    cells[previous].lowMax = 0
                                    // avoid double-inc.
                                    lowInc = 0
                                } else {
                                    cells[cell].isLit = true
                                }
                            }
                        }
                        cells[cell].isLitDelay = false
                    }
                } else {
                    // initialize new cell.
                    cells[cell].init()
                }

                // STEP 2 - check for blocker
                // a dark blocker in shadow's edge will be visible
                if (blocker) {
                    if (cells[cell].isLit || cell != 0 && cells[previous].isLit
                            || isCornerVisible) {
                        // hack: make 'corners' visible
                        isCornerVisible = cells[cell].isLit

                        cells[cell].isLit = false
                        cells[cell].isVisible = true

                        val upper = getUpper(row, cell)
                        if (upper < cells[cell].upMax || cells[cell].upMax == 0) {
                            // new upper shadow
                            cells[cell].upMax = upper
                            cells[cell].upCount = 0
                            upInc = 0
                        }

                        val lower = getLower(row, cell)
                        if (lower > cells[cell].lowMax || cells[cell].lowMax == 0) {
                            // new lower shadow
                            cells[cell].lowMax = lower
                            cells[cell].lowCount = -10
                            lowInc = 0
                            if (lower <= 30) { // somewhat arbitrary
                                cells[cell].isLitDelay = true
                            }
                        }
                    } else {
                        cells[cell].isVisible = false
                    }
                } else {
                    cells[cell].isVisible = false
                }

                // STEP 3 - add increments to upper, lower counts
                cells[cell].upCount += upInc
                cells[cell].lowCount += lowInc

                // STEP 4 - check south for dark
                if (previous >= 0) {
                    if (cells[previous].reachedUpper()) {
                        if (!cells[cell].reachedUpper()) {
                            cells[cell].upMax = cells[previous].upMax
                            cells[cell].upCount = cells[previous].upCount
                            cells[cell].upCount -= cells[previous].upMax
                        }
                        cells[cell].isLit = false
                        cells[cell].isVisible = false
                    }
                }

                // STEP 5 - nuke lower if previous lower
                if (previous >= 0) {
                    if (cells[previous].reachedLower()) {
                        cells[cell].lowMax = cells[previous].lowMax
                        cells[cell].lowCount = cells[previous].lowCount
                        cells[cell].lowCount -= cells[previous].lowMax
                        cells[previous].lowMax = 0
                        cells[previous].lowCount = cells[previous].lowMax
                    }

                    if (cells[previous].lowMax != 0 || !cells[previous].isLit && cells[previous].lowMax == 0) {
                        cells[cell].lowCount = cells[cell].lowMax + 10
                    }
                }

                // STEP 6 - light up if we've reached lower bound
                if (cells[cell].reachedLower()) {
                    cells[cell].isLit = true
                }

                // now place appropriate value in sh
                if (cells[cell].isLit || blocker && cells[cell].isVisible) {
                    result.add(location)
                }

                if (cells[cell].isLit) {
                    isRowDark = false
                }
            } // end for - cells

            isCornerVisible = false // don't carry over to next row. :)
            if (isRowDark) {
                isAllDark = true
            }
        } // end for - rows
    }

    override fun getTemplate(location: Vector2D, scanner: Function<Vector2D, Boolean>, radius: Int): Set<Vector2D> {

        if (radius < 1) {
            throw IllegalArgumentException()
        }

        this.origin = location
        this.scanner = scanner
        this.radius = radius

        circle = CIRCLES[radius]
        result = HashSet<Vector2D>()
        result.add(location)

        for (octant in 0..7) {
            scanOctant(octant)
        }

        return result
    }

    private inner class Cell {

        internal var upCount: Int = 0
        internal var upMax: Int = 0
        internal var lowCount: Int = 0
        internal var lowMax: Int = 0
        internal var isLit: Boolean = false
        internal var isLitDelay: Boolean = false
        internal var isVisible: Boolean = false // for blockers only

        init {
            init()
        }

        internal fun init() {
            upCount = 0
            upMax = 0
            lowCount = 0
            lowMax = 0
            isLit = true
            isVisible = true
            isLitDelay = false
        }

        internal fun reachedLower(): Boolean {
            // integer math: a 'step' has a value of 10
            // see if we're within a half step of the max. VERY important
            // to use 'half step' or else things look really stupid.
            return lowMax != 0 && lowCount + 5 >= lowMax && lowCount - 5 < lowMax
        }

        internal fun reachedUpper(): Boolean {
            // see if we're within a half step of the max. VERY important
            // to use 'half step' or else things look really stupid.
            return upMax != 0 && upCount + 5 >= upMax && upCount - 5 < upMax
        }

    }

    companion object {

        private val MAX_LIGHT_RADIUS = 20
        private val CIRC_MAX = 32000
        private val BIG_SHADOW = 32000
        private val VIEW = 2 // 1=widest LOS .. 5=narrowest
        // for easy x,y octant translation
        private val ROW_TRANSFORM = arrayOf(Vector2D(1, 0), Vector2D(0, 1), Vector2D(0, 1), Vector2D(-1, 0), Vector2D(-1, 0), Vector2D(0, -1), Vector2D(0, -1), Vector2D(1, 0))
        private val CELL_TRANSFORM = arrayOf(Vector2D(0, 1), Vector2D(1, 0), Vector2D(-1, 0), Vector2D(0, 1), Vector2D(0, -1), Vector2D(-1, 0), Vector2D(1, 0), Vector2D(0, -1))

        private val CIRCLES = arrayOfNulls<IntArray>(20)

        init {
            for (radius in 1..19) {
                val circle = IntArray(radius + 1)
                CIRCLES[radius] = circle

                // note that rows 0 and 1 will always go to infinity.
                circle[1] = CIRC_MAX
                circle[0] = circle[1]

                // for the rest, simply calculate max height based on radius.
                for (i in 2..radius) {
                    // check top
                    val i2 = i * i
                    val radius2 = radius * radius
                    if (2 * i2 <= radius2) {
                        circle[i] = CIRC_MAX
                    } else {
                        for (j in i - 1 downTo 0) {
                            // check that Distance (I^2 + J^2) is no more than (R+0.5)^2
                            // this rounding allows for *much* better looking circles.
                            if (i2 + j * j <= radius2 + radius) {
                                circle[i] = j
                                break
                            }
                        }
                    }
                }
            }
        }
    }

}
