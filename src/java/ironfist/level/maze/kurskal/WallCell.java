package ironfist.level.maze.kurskal;

class WallCell {

  int x;
  int y;
  int left;
  int right;

  WallCell(int x, int y, int left, int right) {
    this.x = x;
    this.y = y;
    this.left = left;
    this.right = right;
  }

  public String toString() {
    return "(" + x + "," + y + ")=[" + left + "," + right + "]";
  }
}