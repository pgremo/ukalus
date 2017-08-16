package ukalus.util

fun Int.compareToAndSwap(other: Int) = if (this < other) Pair(this, other) else Pair(other, this)