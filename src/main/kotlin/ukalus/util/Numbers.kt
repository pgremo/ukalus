package ukalus.util

fun Int.compareToAndSwap(other: Int): Pair<Int, Int> = if (this < other) Pair(this, other) else Pair(other, this)