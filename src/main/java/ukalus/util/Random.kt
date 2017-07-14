package ukalus.util

import java.util.*

fun Random.nextInt(min: Int, max: Int) = min + this.nextInt(max - min)