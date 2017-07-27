package ukalus

import ukalus.math.Vector2D

import java.util.ArrayList

/**
 * DOCUMENT ME!

 * @author pmgremo
 */
class RecursiveShadowCastingVision {

    private var level: Level? = null
    private var maxRadius: Int = 0
    private var seen: MutableList<Tile>? = null
    private var xCenter: Int = 0
    private var yCenter: Int = 0

    /**
     * DOCUMENT ME!

     * @param x1
     * *          DOCUMENT ME!
     * *
     * @param y1
     * *          DOCUMENT ME!
     * *
     * @param x2
     * *          DOCUMENT ME!
     * *
     * @param y2
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    private fun slope(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        val xDiff = x1 - x2
        val yDiff = y1 - y2

        return if (yDiff != 0.0) xDiff / yDiff else 0.0
    }

    /**
     * DOCUMENT ME!

     * @param x1
     * *          DOCUMENT ME!
     * *
     * @param y1
     * *          DOCUMENT ME!
     * *
     * @param x2
     * *          DOCUMENT ME!
     * *
     * @param y2
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    private fun invSlope(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        val slope = slope(x1, y1, x2, y2)

        return if (slope != 0.0) 1 / slope else 0.0
    }

    /**
     * DOCUMENT ME!

     * @param distance
     * *          DOCUMENT ME!
     * *
     * @param startSlope
     * *          DOCUMENT ME!
     * *
     * @param endSlope
     * *          DOCUMENT ME!
     */
    private fun scanNW2N(distance: Int, start: Double, end: Double) {
        var startSlope = start
        val endSlope = end
        if (distance > maxRadius) {
            return
        }

        // calculate start and end cell of the scan
        val xStart = (xCenter + 0.5 - startSlope * distance).toInt()
        val xEnd = (xCenter + 0.5 - endSlope * distance).toInt()
        val yCheck = yCenter - distance

        // is the starting cell the leftmost cell in the octant?
        // NO: call applyCell() to starting cell
        // YES: it has already been applied in start()
        if (xStart != xCenter - 1 * distance) {
            applyCell(xStart, yCheck)
        }

        // find out if starting cell blocks LOS
        var prevBlocked = scanCell(xStart, yCheck)

        // scan from the cell after the starting cell (xStart+1) to end cell of
        // scan (xCheck<=xEnd)
        for (xCheck in xStart + 1..xEnd) {
            // is the current cell the rightmost cell in the octant?
            // NO: call applyCell() to current cell
            // YES: it has already been applied in start()
            if (xCheck != xCenter) {
                // apply cell
                applyCell(xCheck, yCheck)
            }

            // cell blocks LOS
            // if previous cell didn't block LOS (prevBlocked==false) we have
            // hit a 'new' section of walls. a new scan will be started with an
            // endSlope that 'brushes' by to the left of the blocking cell
            //
            // +---+a####+---+ @ = [xCenter+0.5,yCenter+0.5]
            // | |#####| | a = old [xCheck,yCheck]
            // | |#####| | b = new [xCheck-0.00001,yCheck+0.99999]
            // | |#####| |
            // +---b#####+---+
            // +---++---++---+
            // | || || |
            // | || || @ |
            // | || || |
            // +---++---++---+
            //
            if (scanCell(xCheck, yCheck)) {
                if (!prevBlocked) {
                    scanNW2N(distance + 1, startSlope, slope(xCenter + 0.5,
                            yCenter + 0.5, xCheck - 0.000001, yCheck + 0.999999))
                }

                prevBlocked = true
            } else {
                if (prevBlocked) {
                    startSlope = slope(xCenter + 0.5, yCenter + 0.5, xCheck.toDouble(), yCheck.toDouble())
                }

                prevBlocked = false
            }// cell doesn't block LOS
            // if the cell is the first non-blocking cell after a section of walls
            // we need to calculate a new startSlope that 'brushes' by to the right
            // of the blocking cells
            //
            // #####a---++---+ @ = [xCenter+0.5,yCenter+0.5]
            // #####| || | a = new and old [xCheck,yCheck]
            // #####| || |
            // #####| || |
            // #####+---++---+
            // +---++---++---+
            // | || || |
            // | || || @ |
            // | || || |
            // +---++---++---+
            //
        }

        // if the last cell of the scan didn't block LOS a new scan should be
        // started
        if (!prevBlocked) {
            scanNW2N(distance + 1, startSlope, endSlope)
        }
    }

    /**
     * DOCUMENT ME!

     * @param distance
     * *          DOCUMENT ME!
     * *
     * @param startSlope
     * *          DOCUMENT ME!
     * *
     * @param endSlope
     * *          DOCUMENT ME!
     */
    private fun scanNE2N(distance: Int, start: Double, end: Double) {
        var startSlope = start
        val endSlope = end
        if (distance > maxRadius) {
            return
        }

        // calculate start and end cell of the scan
        val xStart = (xCenter + 0.5 - startSlope * distance).toInt()
        val xEnd = (xCenter + 0.5 - endSlope * distance).toInt()
        val yCheck = yCenter - distance

        // is starting cell the rightmost cell in the octant?
        // NO: call applyCell() to starting cell
        // YES: it has already been applied in start()
        if (xStart != xCenter - -1 * distance) {
            applyCell(xStart, yCheck)
        }

        // find out if starting cell blocks LOS
        var prevBlocked = scanCell(xStart, yCheck)

        // scan from the cell after the starting cell (xStart-1) to end cell of
        // scan (xCheck>=xEnd)
        for (xCheck in xStart - 1 downTo xEnd) {
            // is the current cell the leftmost cell in the octant?
            // NO: call applyCell() to current cell
            // YES: it has already been applied in start()
            if (xCheck != xCenter) {
                // apply cell
                applyCell(xCheck, yCheck)
            }

            // cell blocks LOS
            // if previous cell didn't block LOS (prevBlocked==false) we have
            // hit a 'new' section of walls. a new scan will be started with an
            // endSlope that 'brushes' by to the right of the blocking cell
            //
            // +---+a####+---+ @ = [xCenter+0.5,yCenter+0.5]
            // | |#####| | a = old [xCheck,yCheck]
            // | |#####| | b = new [xCheck+0.9999,yCheck-0.00001]
            // | |#####| |
            // +---+#####b---+
            // +---++---++---+
            // | || || |
            // | @ || || |
            // | || || |
            // +---++---++---+
            //
            if (scanCell(xCheck, yCheck)) {
                if (!prevBlocked) {
                    scanNE2N(distance + 1, startSlope, slope(xCenter + 0.5,
                            yCenter + 0.5, xCheck.toDouble() + 1, yCheck + 0.99999))
                }

                prevBlocked = true
            } else {
                if (prevBlocked) {
                    startSlope = slope(xCenter + 0.5, yCenter + 0.5, xCheck + 0.9999999,
                            yCheck.toDouble())
                }

                prevBlocked = false
            }// cell doesn't block LOS
            // if the cell is the first non-blocking cell after a section of walls
            // we need to calculate a new startSlope that 'brushes' by to the left
            // of the blocking cells
            //
            // +---+a---b##### @ = [xCenter+0.5,yCenter+0.5]
            // | || |##### a = old [xCheck,yCheck]
            // | || |##### b = new [xCheck+0.99999,yCheck]
            // | || |#####
            // +---++---+#####
            // +---++---++---+
            // | || || |
            // | @ || || |
            // | || || |
            // +---++---++---+
            //
        }

        // if the last cell of the scan didn't block LOS a new scan should be
        // started
        if (!prevBlocked) {
            scanNE2N(distance + 1, startSlope, endSlope)
        }
    }

    /**
     * DOCUMENT ME!

     * @param distance
     * *          DOCUMENT ME!
     * *
     * @param startSlope
     * *          DOCUMENT ME!
     * *
     * @param endSlope
     * *          DOCUMENT ME!
     */
    private fun scanNW2W(distance: Int, start: Double, end: Double) {
        var startSlope = start
        val endSlope = end
        if (distance > maxRadius) {
            return
        }

        // calculate start and end cell of the scan
        val yStart = (yCenter + 0.5 - startSlope * distance).toInt()
        val yEnd = (yCenter + 0.5 - endSlope * distance).toInt()
        val xCheck = xCenter - distance

        // is starting cell the topmost cell in the octant?
        // NO: call applyCell() to starting cell
        // YES: it has already been applied in start()
        if (yStart != yCenter - 1 * distance) {
            applyCell(xCheck, yStart)
        }

        // find out if starting cell blocks LOS
        var prevBlocked = scanCell(xCheck, yStart)

        // scan from the cell after the starting cell (yStart+1) to end cell of
        // scan (yCheck<=yEnd)
        for (yCheck in yStart + 1..yEnd) {
            // is the current cell the bottommost cell in the octant?
            // NO: call applyCell() to current cell
            // YES: it has already been applied in start()
            if (yCheck != yCenter) {
                // apply cell
                applyCell(xCheck, yCheck)
            }

            // cell blocks LOS
            // if previous cell didn't block LOS (prevBlocked==false) we have
            // hit a 'new' section of walls. a new scan will be started with an
            // endSlope that 'brushes' by the top of the blocking cell (see fig.)
            //
            // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
            // | || || | a = old [xCheck,yCheck]
            // | || || | b = new [xCheck+0.99999,yCheck-0.00001]
            // | || || |
            // +---b+---++---+
            // a####+---++---+
            // #####| || |
            // #####| || |
            // #####| || |
            // #####+---++---+
            // +---++---++---+
            // | || || |
            // | || || @ |
            // | || || |
            // +---++---++---+
            //
            if (scanCell(xCheck, yCheck)) {
                if (!prevBlocked) {
                    scanNW2W(distance + 1, startSlope, invSlope(xCenter + 0.5,
                            yCenter + 0.5, xCheck + 0.99999, yCheck - 0.00001))
                }

                prevBlocked = true
            } else {
                if (prevBlocked) {
                    startSlope = invSlope(xCenter + 0.5, yCenter + 0.5, xCheck.toDouble(), yCheck.toDouble())
                }

                prevBlocked = false
            }// cell doesn't block LOS
            // if the cell is the first non-blocking cell after a section of walls
            // we need to calculate a new startSlope that 'brushes' by the bottom
            // of the blocking cells
            //
            // #####+---++---+ @ = [xCenter+0.5,yCenter+0.5]
            // #####| || | a = old and new [xCheck,yCheck]
            // #####| || |
            // #####| || |
            // #####+---++---+
            // a---++---++---+
            // | || || |
            // | || || |
            // | || || |
            // +---++---++---+
            // +---++---++---+
            // | || || |
            // | || || @ |
            // | || || |
            // +---++---++---+
            //
        }

        // if the last cell of the scan didn't block LOS a new scan should be
        // started
        if (!prevBlocked) {
            scanNW2W(distance + 1, startSlope, endSlope)
        }
    }

    /**
     * DOCUMENT ME!

     * @param distance
     * *          DOCUMENT ME!
     * *
     * @param startSlope
     * *          DOCUMENT ME!
     * *
     * @param endSlope
     * *          DOCUMENT ME!
     */
    private fun scanSW2W(distance: Int, start: Double, end: Double) {
        var startSlope = start
        val endSlope = end
        if (distance > maxRadius) {
            return
        }

        // calculate start and end cell of the scan
        val yStart = (yCenter + 0.5 - startSlope * distance).toInt()
        val yEnd = (yCenter + 0.5 - endSlope * distance).toInt()
        val xCheck = xCenter - distance

        // is starting cell the bottommost cell in the octant?
        // NO: call applyCell() to starting cell
        // YES: it has already been applied in start()
        if (yStart != yCenter - -1 * distance) {
            applyCell(xCheck, yStart)
        }

        // find out if starting cell blocks LOS
        var prevBlocked = scanCell(xCheck, yStart)

        // scan from the cell after the starting cell (yStart-1) to end cell of
        // scan (yCheck>=yEnd)
        for (yCheck in yStart - 1 downTo yEnd) {
            // is the current cell the topmost cell in the octant?
            // NO: call applyCell() to current cell
            // YES: it has already been applied in start()
            if (yCheck != yCenter) {
                // apply cell
                applyCell(xCheck, yCheck)
            }

            // cell blocks LOS
            // if previous cell didn't block LOS (prevBlocked==false) we have
            // hit a 'new' section of walls. a new scan will be started with an
            // endSlope that 'brushes' by the bottom of the blocking cell
            //
            // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
            // | || || | a = old [xCheck,yCheck]
            // | || || @ | b = new [xCheck+0.99999,yCheck+1]
            // | || || |
            // +---++---++---+
            // a####+---++---+
            // #####| || |
            // #####| || |
            // #####| || |
            // #####+---++---+
            // +---b+---++---+
            // | || || |
            // | || || |
            // | || || |
            // +---++---++---+
            //
            if (scanCell(xCheck, yCheck)) {
                if (!prevBlocked) {
                    scanSW2W(distance + 1, startSlope, invSlope(xCenter + 0.5,
                            yCenter + 0.5, xCheck + 0.99999, yCheck.toDouble() + 1))
                }

                prevBlocked = true
            } else {
                if (prevBlocked) {
                    startSlope = invSlope(xCenter + 0.5, yCenter + 0.5, xCheck.toDouble(),
                            yCheck + 0.99999)
                }

                prevBlocked = false
            }// cell doesn't block LOS
            // if the cell is the first non-blocking cell after a section of walls
            // we need to calculate a new startSlope that 'brushes' by the top of
            // the blocking cells
            //
            // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
            // | || || | a = old [xCheck,yCheck]
            // | || || @ | b = new [xCheck,yCheck+0.99999]
            // | || || |
            // +---++---++---+
            // a---++---++---+
            // | || || |
            // | || || |
            // | || || |
            // b---++---++---+
            // #####+---++---+
            // #####| || |
            // #####| || |
            // #####| || |
            // #####+---++---+
            //
        }

        // if the last cell of the scan didn't block LOS a new scan should be
        // started
        if (!prevBlocked) {
            scanSW2W(distance + 1, startSlope, endSlope)
        }
    }

    /**
     * DOCUMENT ME!

     * @param distance
     * *          DOCUMENT ME!
     * *
     * @param startSlope
     * *          DOCUMENT ME!
     * *
     * @param endSlope
     * *          DOCUMENT ME!
     */
    private fun scanSW2S(distance: Int, start: Double, end: Double) {
        var startSlope = start
        val endSlope = end
        if (distance > maxRadius) {
            return
        }

        // calculate start and end cell of the scan
        val xStart = (xCenter.toDouble() + 0.5 + startSlope * distance).toInt()
        val xEnd = (xCenter.toDouble() + 0.5 + endSlope * distance).toInt()
        val yCheck = yCenter + distance

        // is the starting cell the leftmost cell in the octant?
        // NO: call applyCell() to starting cell
        // YES: it has already been applied in start()
        if (xStart != xCenter + -1 * distance) {
            applyCell(xStart, yCheck)
        }

        // find out if starting cell blocks LOS
        var prevBlocked = scanCell(xStart, yCheck)

        // scan from the cell after the starting cell (xStart+1) to end cell of
        // scan (xCheck<=xEnd)
        for (xCheck in xStart + 1..xEnd) {
            // is the current cell the rightmost cell in the octant?
            // NO: call applyCell() to current cell
            // YES: it has already been applied in start()
            if (xCheck != xCenter) {
                // apply cell
                applyCell(xCheck, yCheck)
            }

            // cell blocks LOS
            // if previous cell didn't block LOS (prevBlocked==false) we have
            // hit a 'new' section of walls. a new scan will be started with an
            // endSlope that 'brushes' by to the left of the blocking cell
            //
            // +---++---++---+
            // | || || |
            // | || || @ |
            // | || || |
            // +---++---++---+
            // +---ba####+---+ @ = [xCenter+0.5,yCenter+0.5]
            // | |#####| | a = old [xCheck,yCheck]
            // | |#####| | b = new [xCheck-0.00001,yCheck]
            // | |#####| |
            // +---+#####+---+
            //
            if (scanCell(xCheck, yCheck)) {
                if (!prevBlocked) {
                    scanSW2S(distance + 1, startSlope, slope(xCenter + 0.5,
                            yCenter + 0.5, xCheck - 0.00001, yCheck.toDouble()))
                }

                prevBlocked = true
            } else {
                if (prevBlocked) {
                    startSlope = slope(xCenter + 0.5, yCenter + 0.5, xCheck.toDouble(),
                            yCheck + 0.99999)
                }

                prevBlocked = false
            }// cell doesn't block LOS
            // if the cell is the first non-blocking cell after a section of walls
            // we need to calculate a new startSlope that 'brushes' by to the right
            // of the blocking cells
            //
            // +---++---++---+
            // | || || |
            // | || || @ |
            // | || || |
            // +---++---++---+
            // #####a---++---+ @ = [xCenter+0.5,yCenter+0.5]
            // #####| || | a = old [xCheck,yCheck]
            // #####| || | b = new [xCheck,yCheck+0.99999]
            // #####| || |
            // #####b---++---+
            //
        }

        // if the last cell of the scan didn't block LOS a new scan should be
        // started
        if (!prevBlocked) {
            scanSW2S(distance + 1, startSlope, endSlope)
        }
    }

    /**
     * DOCUMENT ME!

     * @param distance
     * *          DOCUMENT ME!
     * *
     * @param startSlope
     * *          DOCUMENT ME!
     * *
     * @param endSlope
     * *          DOCUMENT ME!
     */
    private fun scanSE2S(distance: Int, start: Double, end: Double) {
        var startSlope = start
        val endSlope = end
        if (distance > maxRadius) {
            return
        }

        // calculate start and end cell of the scan
        val xStart = (xCenter.toDouble() + 0.5 + startSlope * distance).toInt()
        val xEnd = (xCenter.toDouble() + 0.5 + endSlope * distance).toInt()
        val yCheck = yCenter + distance

        // is starting cell the rightmost cell in the octant?
        // NO: call applyCell() to starting cell
        // YES: it has already been applied in start()
        if (xStart != xCenter + 1 * distance) {
            applyCell(xStart, yCheck)
        }

        // find out if starting cell blocks LOS
        var prevBlocked = scanCell(xStart, yCheck)

        // scan from the cell after the starting cell (xStart-1) to end cell of
        // scan (xCheck>=xEnd)
        for (xCheck in xStart - 1 downTo xEnd) {
            // is the current cell the leftmost cell in the octant?
            // NO: call applyCell() to current cell
            // YES: it has already been applied in start()
            if (xCheck != xCenter) {
                // apply cell
                applyCell(xCheck, yCheck)
            }

            // cell blocks LOS
            // if previous cell didn't block LOS (prevBlocked==false) we have
            // hit a 'new' section of walls. a new scan will be started with an
            // endSlope that 'brushes' by to the right of the blocking cell
            //
            // +---++---++---+
            // | || || |
            // | @ || || |
            // | || || |
            // +---++---++---+
            // +---+a####b---+ @ = [xCenter+0.5,yCenter+0.5]
            // | |#####| | a = old [xCheck,yCheck]
            // | |#####| | b = new [xCheck+1,yCheck]
            // | |#####| |
            // +---+#####+---+
            //
            if (scanCell(xCheck, yCheck)) {
                if (!prevBlocked) {
                    scanSE2S(distance + 1, startSlope, slope(xCenter + 0.5,
                            yCenter + 0.5, xCheck.toDouble() + 1, yCheck.toDouble()))
                }

                prevBlocked = true
            } else {
                if (prevBlocked) {
                    startSlope = slope(xCenter + 0.5, yCenter + 0.5, xCheck + 0.99999,
                            yCheck + 0.99999)
                }

                prevBlocked = false
            }// cell doesn't block LOS
            // if the cell is the first non-blocking cell after a section of walls
            // we need to calculate a new startSlope that 'brushes' by to the left
            // of the blocking cells
            //
            // +---++---++---+
            // | || || |
            // | @ || || |
            // | || || |
            // +---++---++---+
            // +---+a---+##### @ = [xCenter+0.5,yCenter+0.5]
            // | || |##### a = old [xCheck,yCheck]
            // | || |##### b = new [xCheck+0.99999,yCheck+0.99999]
            // | || |#####
            // +---++---b#####
            //
        }

        // if the last cell of the scan didn't block LOS a new scan should be
        // started
        if (!prevBlocked) {
            scanSE2S(distance + 1, startSlope, endSlope)
        }
    }

    /**
     * DOCUMENT ME!

     * @param distance
     * *          DOCUMENT ME!
     * *
     * @param startSlope
     * *          DOCUMENT ME!
     * *
     * @param endSlope
     * *          DOCUMENT ME!
     */
    private fun scanNE2E(distance: Int, start: Double, end: Double) {
        var startSlope = start
        val endSlope = end
        if (distance > maxRadius) {
            return
        }

        // calculate start and end cell of the scan
        val yStart = (yCenter.toDouble() + 0.5 + startSlope * distance).toInt()
        val yEnd = (yCenter.toDouble() + 0.5 + endSlope * distance).toInt()
        val xCheck = xCenter + distance

        // is starting cell the topmost cell in the octant?
        // NO: call applyCell() to starting cell
        // YES: it has already been applied in start()
        if (yStart != yCenter + -1 * distance) {
            applyCell(xCheck, yStart)
        }

        // find out if starting cell blocks LOS
        var prevBlocked = scanCell(xCheck, yStart)

        // scan from the cell after the starting cell (yStart+1) to end cell of
        // scan (yCheck<=yEnd)
        for (yCheck in yStart + 1..yEnd) {
            // is the current cell the bottommost cell in the octant?
            // NO: call applyCell() to current cell
            // YES: it has already been applied in start()
            if (yCheck != yCenter) {
                // apply cell
                applyCell(xCheck, yCheck)
            }

            // cell blocks LOS
            // if previous cell didn't block LOS (prevBlocked==false) we have
            // hit a 'new' section of walls. a new scan will be started with an
            // endSlope that 'brushes' by the top of the blocking cell (see fig.)
            //
            // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
            // | || || | a = old [xCheck,yCheck]
            // | || || | b = new [xCheck,yCheck-0.00001]
            // | || || |
            // +---++---+b---+
            // +---++---+a####
            // | || |#####
            // | || |#####
            // | || |#####
            // +---++---+#####
            // +---++---++---+
            // | || || |
            // | @ || || |
            // | || || |
            // +---++---++---+
            //
            if (scanCell(xCheck, yCheck)) {
                if (!prevBlocked) {
                    scanNE2E(distance + 1, startSlope, invSlope(xCenter + 0.5,
                            yCenter + 0.5, xCheck.toDouble(), yCheck - 0.00001))
                }

                prevBlocked = true
            } else {
                if (prevBlocked) {
                    startSlope = invSlope(xCenter + 0.5, yCenter + 0.5, xCheck + 0.99999,
                            yCheck.toDouble())
                }

                prevBlocked = false
            }// cell doesn't block LOS
            // if the cell is the first non-blocking cell after a section of walls
            // we need to calculate a new startSlope that 'brushes' by the bottom
            // of the blocking cells
            //
            // +---++---+##### @ = [xCenter+0.5,yCenter+0.5]
            // | || |##### a = old [xCheck,yCheck]
            // | || |##### b = new [xCheck+0.99999,yCheck]
            // | || |#####
            // +---++---+#####
            // +---++---+a---b
            // | || || |
            // | || || |
            // | || || |
            // +---++---++---+
            // +---++---++---+
            // | || || |
            // | @ || || |
            // | || || |
            // +---++---++---+
            //
        }

        // if the last cell of the scan didn't block LOS a new scan should be
        // started
        if (!prevBlocked) {
            scanNE2E(distance + 1, startSlope, endSlope)
        }
    }

    /**
     * DOCUMENT ME!

     * @param distance
     * *          DOCUMENT ME!
     * *
     * @param startSlope
     * *          DOCUMENT ME!
     * *
     * @param endSlope
     * *          DOCUMENT ME!
     */
    private fun scanSE2E(distance: Int, start: Double, end: Double) {
        var startSlope = start
        val endSlope = end
        if (distance > maxRadius) {
            return
        }

        // calculate start and end cell of the scan
        val yStart = (yCenter.toDouble() + 0.5 + startSlope * distance).toInt()
        val yEnd = (yCenter.toDouble() + 0.5 + endSlope * distance).toInt()
        val xCheck = xCenter + distance

        // is starting cell the bottommost cell in the octant?
        // NO: call applyCell() to starting cell
        // YES: it has already been applied in start()
        if (yStart != yCenter + 1 * distance) {
            applyCell(xCheck, yStart)
        }

        // find out if starting cell blocks LOS
        var prevBlocked = scanCell(xCheck, yStart)

        // scan from the cell after the starting cell (yStart-1) to end cell of
        // scan (yCheck>=yEnd)
        for (yCheck in yStart - 1 downTo yEnd) {
            // is the current cell the topmost cell in the octant?
            // NO: call applyCell() to current cell
            // YES: it has already been applied in start()
            if (yCheck != yCenter) {
                // apply cell
                applyCell(xCheck, yCheck)
            }

            // cell blocks LOS
            // if previous cell didn't block LOS (prevBlocked==false) we have
            // hit a 'new' section of walls. a new scan will be started with an
            // endSlope that 'brushes' by the bottom of the blocking cell
            //
            // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
            // | || || | a = old [xCheck,yCheck]
            // | @ || || | b = new [xCheck,yCheck+1]
            // | || || |
            // +---++---++---+
            // +---++---+a####
            // | || |#####
            // | || |#####
            // | || |#####
            // +---++---+#####
            // +---++---+b---+
            // | || || |
            // | || || |
            // | || || |
            // +---++---++---+
            //
            if (scanCell(xCheck, yCheck)) {
                if (!prevBlocked) {
                    scanSE2E(distance + 1, startSlope, invSlope(xCenter + 0.5,
                            yCenter + 0.5, xCheck.toDouble(), yCheck.toDouble() + 1))
                }

                prevBlocked = true
            } else {
                if (prevBlocked) {
                    startSlope = invSlope(xCenter + 0.5, yCenter + 0.5, xCheck + 0.99999,
                            yCheck + 0.99999)
                }

                prevBlocked = false
            }// cell doesn't block LOS
            // if the cell is the first non-blocking cell after a section of walls
            // we need to calculate a new startSlope that 'brushes' by the top of
            // the blocking cells
            //
            // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
            // | || || | a = old [xCheck,yCheck]
            // | @ || || | b = new [xCheck+0.99999,yCheck+0.99999]
            // | || || |
            // +---++---++---+
            // +---++---+a---+
            // | || || |
            // | || || |
            // | || || |
            // +---++---++---b
            // +---++---+#####
            // | || |#####
            // | || |#####
            // | || |#####
            // +---++---+#####
            //
        }

        // if the last cell of the scan didn't block LOS a new scan should be
        // started
        if (!prevBlocked) {
            scanSE2E(distance + 1, startSlope, endSlope)
        }
    }

    /**
     * DOCUMENT ME!

     * @param map
     * *          DOCUMENT ME!
     * *
     * @param x
     * *          DOCUMENT ME!
     * *
     * @param y
     * *          DOCUMENT ME!
     * *
     * @param maxRadius
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    fun getSeen(map: Level?, x: Int, y: Int, maxRadius: Int): List<Tile> {
        this.level = map
        this.xCenter = x
        this.yCenter = y
        this.maxRadius = maxRadius
        seen = ArrayList<Tile>()

        if (map != null) {

            // apply starting cell
            applyCell(x, y)

            if (maxRadius > 0) {
                // scan and apply north
                // until a blocking cell is hit or
                // until maxRadius is reached
                var nL: Int

                nL = 1
                while (nL <= maxRadius) {
                    applyCell(x, y - nL)

                    if (scanCell(x, y - nL)) {
                        break
                    }
                    nL++
                }

                // scan and apply north east
                // until a blocking cell is hit or
                // until maxRadius is reached
                var neL: Int

                neL = 1
                while (neL <= maxRadius) {
                    applyCell(x + neL, y - neL)

                    if (scanCell(x + neL, y - neL)) {
                        break
                    }
                    neL++
                }

                // scan and apply east
                // until a blocking cell is hit or
                // until maxRadius is reached
                var eL: Int

                eL = 1
                while (eL <= maxRadius) {
                    applyCell(x + eL, y)

                    if (scanCell(x + eL, y)) {
                        break
                    }
                    eL++
                }

                // scan and apply south east
                // until a blocking cell is hit or
                // until maxRadius is reached
                var seL: Int

                seL = 1
                while (seL <= maxRadius) {
                    applyCell(x + seL, y + seL)

                    if (scanCell(x + seL, y + seL)) {
                        break
                    }
                    seL++
                }

                // scan and apply south
                // until a blocking cell is hit or
                // until maxRadius is reached
                var sL: Int

                sL = 1
                while (sL <= maxRadius) {
                    applyCell(x, y + sL)

                    if (scanCell(x, y + sL)) {
                        break
                    }
                    sL++
                }

                // scan and apply south west
                // until a blocking cell is hit or
                // until maxRadius is reached
                var swL: Int

                swL = 1
                while (swL <= maxRadius) {
                    applyCell(x - swL, y + swL)

                    if (scanCell(x - swL, y + swL)) {
                        break
                    }
                    swL++
                }

                // scan and apply west
                // until a blocking cell is hit or
                // until maxRadius is reached
                var wL: Int

                wL = 1
                while (wL <= maxRadius) {
                    applyCell(x - wL, y)

                    if (scanCell(x - wL, y)) {
                        break
                    }
                    wL++
                }

                // scan and apply north west
                // until a blocking cell is hit or
                // until maxRadius is reached
                var nwL: Int

                nwL = 1
                while (nwL <= maxRadius) {
                    applyCell(x - nwL, y - nwL)

                    if (scanCell(x - nwL, y - nwL)) {
                        break
                    }
                    nwL++
                }

                // scan the octant covering the area from north west to north
                // if it isn't blocked
                if (nL != 1 || nwL != 1) {
                    scanNW2N(1, 1.0, 0.0)
                }

                // scan the octant covering the area from north east to north
                // if it isn't blocked
                if (nL != 1 || neL != 1) {
                    scanNE2N(1, -1.0, 0.0)
                }

                // scan the octant covering the area from north west to west
                // if it isn't blocked
                if (nwL != 1 || wL != 1) {
                    scanNW2W(1, 1.0, 0.0)
                }

                // scan the octant covering the area from south west to west
                // if it isn't blocked
                if (swL != 1 || wL != 1) {
                    scanSW2W(1, -1.0, 0.0)
                }

                // scan the octant covering the area from south west to south
                // if it isn't blocked
                if (swL != 1 || sL != 1) {
                    scanSW2S(1, -1.0, 0.0)
                }

                // scan the octant covering the area from south east to south
                // if it isn't blocked
                if (seL != 1 || sL != 1) {
                    scanSE2S(1, 1.0, 0.0)
                }

                // scan the octant covering the area from north east to east
                // if it isn't blocked
                if (neL != 1 || eL != 1) {
                    scanNE2E(1, -1.0, 0.0)
                }

                // scan the octant covering the area from south east to east
                // if it isn't blocked
                if (seL != 1 || eL != 1) {
                    scanSE2E(1, 1.0, 0.0)
                }
            }
        }

        return seen!!
    }

    /**
     * DOCUMENT ME!

     * @param x
     * *          DOCUMENT ME!
     * *
     * @param y
     * *          DOCUMENT ME!
     * *
     * *
     * @return DOCUMENT ME!
     */
    private fun scanCell(x: Int, y: Int): Boolean {
        val tile = level!![Vector2D(x, y)]
        val type = tile!!.tileType
        var result = true

        if (type is Floor) {
            val door = type.door

            result = !(door == null || door.isOpen)
        }
        return result
    }

    /**
     * DOCUMENT ME!

     * @param x
     * *          DOCUMENT ME!
     * *
     * @param y
     * *          DOCUMENT ME!
     */
    private fun applyCell(x: Int, y: Int) {
        val newX = Math.abs(x - xCenter)
        val newY = Math.abs(y - yCenter)
        if (Math.max(newX, newY) + Math.min(newX, newY) / 2 <= maxRadius) {
            val tile = level!![Vector2D(x, y)]

            if (tile != null) {
                seen!!.add(tile)
            }
        }
    }
}