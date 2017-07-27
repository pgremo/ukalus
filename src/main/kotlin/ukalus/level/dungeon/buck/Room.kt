package ukalus.level.dungeon.buck

import ukalus.level.Level
import ukalus.level.Region
import ukalus.math.Vector2D
import ukalus.util.sift
import java.util.*

class Room(
        private val random: Random,
        private val height: Int,
        private val width: Int,
        private val id: Int
) : Region<Int> {
    override fun place(location: Vector2D, level: Level<Int>) {
        val startX = location.x
        val startY = location.y

        // interior
        for (x in startX + 1..startX + height - 1 - 1) {
            for (y in startY + 1..startY + width - 1 - 1) {
                level[Vector2D(x, y)] = id
            }
        }

        // corners
        level[Vector2D(startX, startY)] = 0
        level[Vector2D(startX + height - 1, startY)] = 0
        level[Vector2D(startX, startY + width - 1)] = 0
        level[Vector2D(startX + height - 1, startY + width - 1)] = 0

        // edges
        val borders = listOf(
                (startX + 1 until startX + height - 1).map { Pair(Vector2D(it, startY), Vector2D(it, startY - 1)) },
                (startX + 1 until startX + height - 1).map { Pair(Vector2D(it, startY + width - 1), Vector2D(it, startY + width)) },
                (startY + 1 until startY + width - 1).map { Pair(Vector2D(startX, it), Vector2D(startX - 1, it)) },
                (startY + 1 until startY + width - 1).map { Pair(Vector2D(startX + height - 1, it), Vector2D(startX + height, it)) }
        )
        borders.forEach { items -> items.forEach { level[it.first] = 0 } }

        // select door ways
        borders
                .flatMap { items -> items.sift { level.contains(it.second) && level[it.second] == 0 }.asIterable() }
                .map { it[random.nextInt(it.size)] }
                .forEach { level[it.first] = 100 }
    }

    override fun cost(location: Vector2D, level: Level<Int>): Int {
        var result = 0
        val startX = location.x
        val startY = location.y
        if (startY >= 0
                && startY + width <= level.width
                && startX >= 0
                && startX + height <= level.length
                && level[Vector2D(startX, startY)] == 0
                && level[Vector2D(startX, startY + width - 1)] == 0
                && level[Vector2D(startX + height - 1, startY)] == 0
                && level[Vector2D(startX + height - 1, startY + width - 1)] == 0) {

            if (startY > 0 && startY + width < level.width) {
                for (x in startX + 1..startX + height - 1 - 1) {
                    if (level[Vector2D(x, startY - 1)] == 1) {
                        result++
                    }
                    if (level[Vector2D(x, startY + width)] == 1) {
                        result++
                    }
                }
            }
            if (startX > 0 && startX + height < level.length) {
                for (y in startY + 1..startY + width - 1 - 1) {
                    if (level[Vector2D(startX - 1, y)] == 1) {
                        result++
                    }
                    if (level[Vector2D(startX + height, y)] == 1) {
                        result++
                    }
                }
            }
            for (x in startX + 1..startX + height - 1 - 1) {
                for (y in startY + 1..startY + width - 1 - 1) {
                    if (level[Vector2D(x, y)] == 1) {
                        result += 3
                    }
                    if (level[Vector2D(x, y)] > 1) {
                        result += 100
                    }
                }
            }
        }
        return result
    }
}

