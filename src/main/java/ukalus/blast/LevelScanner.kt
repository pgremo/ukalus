package ukalus.blast

import ukalus.level.Level
import ukalus.math.Vector2D
import java.util.function.Function

class LevelScanner(private val level: Level<*>) : Function<Vector2D, Boolean> {

    override fun apply(location: Vector2D) = !level.contains(location) || level[location] == null

}
