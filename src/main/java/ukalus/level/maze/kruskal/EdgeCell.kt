package ukalus.level.maze.kruskal

internal class EdgeCell(var x: Int, var y: Int, var left: Int, var right: Int) {

    override fun toString(): String {
        return "($x,$y)=[$left,$right]"
    }
}