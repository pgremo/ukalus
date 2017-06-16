package ukalus.level.maze;

import ukalus.level.Level;
import ukalus.level.Region;
import ukalus.math.Vector2D;

public class MazeRegion implements Region<Integer> {

  private int[][] cells;

  public MazeRegion(int[][] cells) {
    this.cells = cells;
  }

  public void place(Vector2D location, Level<Integer> level) {
    for (int x = 0; x < cells.length; x++) {
      for (int y = 0; y < cells[x].length; y++) {
        Vector2D target = location.add(Vector2D.get(x, y));
        level.set(target, cells[x][y]);
      }
    }
  }

  public int cost(Vector2D location, Level<Integer> level) {
    return 0;
  }

}
