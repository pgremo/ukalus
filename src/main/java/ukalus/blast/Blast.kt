package ukalus.blast

import ukalus.math.Vector2D
import java.util.function.Function

interface Blast {

    fun getTemplate(location: Vector2D, scanner: Function<Vector2D, Boolean>, radius: Int): Set<Vector2D>

}