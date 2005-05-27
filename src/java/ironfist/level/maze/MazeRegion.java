package ironfist.level.maze;

import ironfist.level.Level;
import ironfist.level.Region;
import ironfist.math.Vector2D;

public class MazeRegion implements Region {

  private Vector2D location;
  private int[][] cells;

  public MazeRegion(int[][] cells) {
    this.cells = cells;
  }

  public void place(Level level) {
    for (int x = 0; x < cells.length; x++) {
      for (int y = 0; y < cells[x].length; y++) {
        Vector2D target = location.add(Vector2D.get(x, y));
        level.set(target, cells[x][y]);
      }
    }
  }

  public int cost(Level level) {
    return 0;
  }

  public Vector2D getLocation() {
    return location;
  }

  public void setLocation(Vector2D location) {
    this.location = location;
  }

}
