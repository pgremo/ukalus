package ukalus.level.dungeon.buck

import org.apache.commons.math3.random.MersenneTwister
import org.apache.commons.math3.random.RandomAdaptor
import ukalus.level.Level
import ukalus.level.maze.prim.PrimMazeGenerator
import ukalus.math.Vector2D
import java.util.*

class BuckDungeonGenerator {

    private val random = RandomAdaptor(MersenneTwister())
    private val mazeFactory = PrimMazeGenerator(random, 19, 79)
    private val roomFactory = RoomGenerator(random, 5, 9, 9, 14, 2)
    private val sparseness = 7
    private val maxRooms = 12

    private var cells = mutableListOf<Vector2D>()

    fun generate(): Level<*> {
        val level = Level<Int>(Array(19) { Array(79, { 0 }) })
        for (x in 0..19) {
            for (y in 0..79) {
                cells.add(Vector2D(x, y))
            }
        }

        val maze = mazeFactory.create()
        maze.place(Vector2D(0, 0), level)

        removeDeadEnds(level, sparseness)

        addRooms(level)

        removeDeadEnds(level, cells.size)

        return level
    }

    private fun addRooms(level: Level<Int>) {
        MutableList(maxRooms) { roomFactory.create() }
                .forEach { room ->
                    cells
                            .map { x -> Pair(room.cost(x, level), x) }
                            .filter { (first) -> 0 < first && first < Integer.MAX_VALUE }
                            .minBy { it.first }
                            ?.let { (_, second) -> room.place(second, level) }
                }
    }

    private fun removeDeadEnds(level: Level<Int>, maxSteps: Int) {
        var deadEnds: List<Vector2D> = LinkedList(cells)
        var step = 0
        while (step < maxSteps && !deadEnds.isEmpty()) {
            val ends = LinkedList<Vector2D>()
            for (current in deadEnds) {
                val edges = DIRECTIONS
                        .map { current.plus(it) }
                        .filter { x -> level.contains(x) && level.get(x) > 0 }

                if (edges.size == 1) {
                    level.set(current, 0)
                    ends.add(edges[0])
                }
            }
            deadEnds = ends
            step++
        }
    }

    companion object {

        private val DIRECTIONS = arrayOf(Vector2D(1, 0), Vector2D(0, 1), Vector2D(0, -1), Vector2D(-1, 0))

        @JvmStatic fun main(args: Array<String>) {
            print(BuckDungeonGenerator().generate())
        }
    }
}
