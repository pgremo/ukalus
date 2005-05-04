package ironfist.level.maze.kruskal;

import ironfist.util.Closure;
import ironfist.util.DisjointSet;

class RemoveWall implements Closure<EdgeCell, Object> {

  private static final long serialVersionUID = 3544957636379357497L;

  private DisjointSet sets;
  private int[][] result;

  RemoveWall(DisjointSet sets, int[][] result) {
    this.sets = sets;
    this.result = result;
  }

  public Object apply(EdgeCell wall) {
    if (sets.find(wall.left) != sets.find(wall.right)) {
      sets.union(wall.left, wall.right);
      result[wall.x][wall.y] = 1;
    }
    return wall;
  }

}
