package ukalus.items

import java.util.*

fun Random.nextInt(min: Int, max: Int) = min + this.nextInt(max - min)