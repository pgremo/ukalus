package ukalus.blast

import ukalus.level.Level
import ukalus.math.Vector2D
import java.util.function.Function

class LevelScanner(private val level: Level<*>) : Function<Vector2D, Boolean> {

    override fun apply(location: Vector2D): Boolean {
        return !level.contains(location) || level.get(location) == null
    }

}
