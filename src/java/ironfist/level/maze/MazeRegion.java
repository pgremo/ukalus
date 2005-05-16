package ironfist.level.maze;

import ironfist.level.Region;
import ironfist.math.Vector2D;

public class MazeRegion implements Region {

  private Vector2D location;
  private int[][] cells;

  public MazeRegion(int[][] cells) {
    this.cells = cells;
  }

  public void place(int[][] level) {
    for (int x = 0; x < cells.length; x++) {
      for (int y = 0; y < cells[x].length; y++) {
        Vector2D target = location.add(Vector2D.get(x, y));
        level[target.getX()][target.getY()] = cells[x][y];
      }
    }
  }

  public int cost(int[][] cells) {
    return 0;
  }

  public Vector2D getLocation() {
    return location;
  }

  public void setLocation(Vector2D location) {
    this.location = location;
  }

}
