package ukalus.util

import java.util.*

fun Random.nextInt(min: Int, max: Int) = min + nextInt(max - min)