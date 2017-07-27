package ukalus.util

class DisjointSet(n: Int) {
    private val s: IntArray = IntArray(n) { -1 }

    fun find(x: Int): Int = if (s[x] < 0)
        x
    else {
        s[x] = find(s[x])
        s[x]
    }

    fun union(x: Int, y: Int): Int {
        val r = intArrayOf(find(x), find(y))

        if (r[0] == r[1]) return -1

        val l = if (s[r[0]] < s[r[1]]) 0 else 1

        s[r[l]] += s[r[l xor 1]]
        s[r[l xor 1]] = r[l]

        return -s[r[l]]
    }

    fun size(x: Int): Int = s[x]

    override fun toString() = s.joinToString(prefix = "{ ", postfix = " }")
}
