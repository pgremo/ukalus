package ironfist.level.maze.kurskal;

import ironfist.util.Closure;
import ironfist.util.DisjointSet;

class RemoveWall implements Closure<WallCell, Object> {

  private static final long serialVersionUID = 3544957636379357497L;

  private DisjointSet sets;
  private boolean[][] result;

  RemoveWall(DisjointSet sets, boolean[][] result) {
    this.sets = sets;
    this.result = result;
  }

  public Object apply(WallCell wall) {
    if (sets.find(wall.left) != sets.find(wall.right)) {
      sets.union(wall.left, wall.right);
      result[wall.x][wall.y] = true;
    }
    return wall;
  }

}
