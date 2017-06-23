package ukalus.level.maze

import ukalus.level.Level
import ukalus.level.Region
import ukalus.math.Vector2D

class MazeRegion(private val cells: Array<IntArray>) : Region<Int> {

    override fun place(location: Vector2D, level: Level<Int>) {
        for (x in cells.indices) {
            for (y in 0..cells[x].size - 1) {
                level[location + Vector2D(x, y)] = cells[x][y]
            }
        }
    }

    override fun cost(location: Vector2D, level: Level<Int>) = 0

}
