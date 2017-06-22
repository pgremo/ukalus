package ukalus.items

import java.util.*

fun Random.nextInt(min: Int, max: Int): Int = min + this.nextInt(max - min)