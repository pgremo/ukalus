package ukalus.level

import ukalus.math.Vector2D

interface Region<T> {

    fun place(location: Vector2D, level: Level<T>)

    fun cost(location: Vector2D, level: Level<T>): Int

}