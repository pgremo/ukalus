package ukalus.blast.raytrace;

import ukalus.blast.Blast;
import ukalus.math.Vector2D;
import ukalus.util.Closure;

import java.util.HashSet;
import java.util.Set;

public class RayTracing implements Blast {

  private static final int MAX_LIGHT_RADIUS = 20;
  private static final int CIRC_MAX = 32000;
  private static final int BIG_SHADOW = 32000;
  private static int VIEW = 2; // 1=widest LOS .. 5=narrowest
  // for easy x,y octant translation
  private static final Vector2D[] ROW_TRANSFORM = new Vector2D[]{
      Vector2D.get(1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(0, 1),
      Vector2D.get(-1, 0),
      Vector2D.get(-1, 0),
      Vector2D.get(0, -1),
      Vector2D.get(0, -1),
      Vector2D.get(1, 0)};
  private static final Vector2D[] CELL_TRANSFORM = new Vector2D[]{
      Vector2D.get(0, 1),
      Vector2D.get(1, 0),
      Vector2D.get(-1, 0),
      Vector2D.get(0, 1),
      Vector2D.get(0, -1),
      Vector2D.get(-1, 0),
      Vector2D.get(1, 0),
      Vector2D.get(0, -1)};

  private static final int[][] CIRCLES = new int[20][];

  static {
    for (int radius = 1; radius < 20; radius++) {
      int[] circle = new int[radius + 1];
      CIRCLES[radius] = circle;

      // note that rows 0 and 1 will always go to infinity.
      circle[0] = circle[1] = CIRC_MAX;

      // for the rest, simply calculate max height based on radius.
      for (int i = 2; i <= radius; i++) {
        // check top
        int i2 = i * i;
        int radius2 = radius * radius;
        if (2 * i2 <= radius2) {
          circle[i] = CIRC_MAX;
        } else {
          for (int j = i - 1; j >= 0; j--) {
            // check that Distance (I^2 + J^2) is no more than (R+0.5)^2
            // this rounding allows for *much* better looking circles.
            if (i2 + j * j <= radius2 + radius) {
              circle[i] = j;
              break;
            }
          }
        }
      }
    }
  }

  // the cell array
  private Cell[] cells;

  // the 'circle' array. For any given row, we won't check higher than
  // this given cell.
  private int[] circle;

  // current light radius
  private int radius;
  private Set<Vector2D> result;
  private Vector2D origin;
  private Closure<Vector2D, Boolean> scanner;

  {
    cells = new Cell[MAX_LIGHT_RADIUS];
    for (int i = 0; i < cells.length; i++) {
      cells[i] = new Cell();
    }
  }

  private int getUpper(int x, int y) {
    // got a blocker at row bX, cell bY. do all values
    // and scale by a factor of 10 for the integer math.
    int result = (10 * (10 * x - VIEW)) / (10 * y + VIEW);
    // check upper bound for blocker on diagonal
    if (result < 10) {
      result = 10;
    }

    return result;
  }

  private int getLower(int x, int y) {
    // got a blocker at row bX, cell bY. do all values
    // and scale by a factor of 10 for the integer math.
    int result = BIG_SHADOW;
    if (y != 0) {
      result = (10 * (10 * x + VIEW)) / (10 * y - VIEW);
    }

    return result;
  }

  private void scanOctant(int octant) {
    cells[0].init();

    boolean isAllDark = false;
    boolean isCornerVisible = false;

    for (int row = 1; row <= radius; row++) {
      boolean isRowDark = true;

      int top = circle[row];
      if (top > row) {
        top = row;
      }

      for (int cell = 0; cell <= top; cell++) {
        // check for all_dark - we've finished the octant but
        // have yet to fill in '0' for the rest of the sight grid
        if (isAllDark) {
          continue;
        }

        // translate X,Y coordinate
        Vector2D location = origin.add(ROW_TRANSFORM[octant].multiply(row))
          .add(CELL_TRANSFORM[octant].multiply(cell));

        boolean blocker = scanner.apply(location);

        int upInc = 10;
        int lowInc = 10;
        int previous = cell - 1;

        // STEP 1 - inherit values from immediate West, if possible
        if (cell < row) {
          // check for delayed lighting
          if (cells[cell].isLitDelay) {
            // blockers don't light up with lit_delay.
            if (!blocker) {
              if (cells[previous].isLit) {
                if (cells[previous].lowMax != 0) {
                  cells[cell].isLit = false;
                  // steal lower values
                  cells[cell].lowMax = cells[previous].lowMax;
                  cells[cell].lowCount = cells[previous].lowCount;
                  cells[previous].lowCount = 0;
                  cells[previous].lowMax = 0;
                  // avoid double-inc.
                  lowInc = 0;
                } else {
                  cells[cell].isLit = true;
                }
              }
            }
            cells[cell].isLitDelay = false;
          }
        } else {
          // initialize new cell.
          cells[cell].init();
        }

        // STEP 2 - check for blocker
        // a dark blocker in shadow's edge will be visible
        if (blocker) {
          if (cells[cell].isLit || (cell != 0 && cells[previous].isLit)
              || isCornerVisible) {
            // hack: make 'corners' visible
            isCornerVisible = cells[cell].isLit;

            cells[cell].isLit = false;
            cells[cell].isVisible = true;

            int upper = getUpper(row, cell);
            if (upper < cells[cell].upMax || cells[cell].upMax == 0) {
              // new upper shadow
              cells[cell].upMax = upper;
              cells[cell].upCount = 0;
              upInc = 0;
            }

            int lower = getLower(row, cell);
            if (lower > cells[cell].lowMax || cells[cell].lowMax == 0) {
              // new lower shadow
              cells[cell].lowMax = lower;
              cells[cell].lowCount = -10;
              lowInc = 0;
              if (lower <= 30) { // somewhat arbitrary
                cells[cell].isLitDelay = true;
              }
            }
          } else {
            cells[cell].isVisible = false;
          }
        } else {
          cells[cell].isVisible = false;
        }

        // STEP 3 - add increments to upper, lower counts
        cells[cell].upCount += upInc;
        cells[cell].lowCount += lowInc;

        // STEP 4 - check south for dark
        if (previous >= 0) {
          if (cells[previous].reachedUpper()) {
            if (!cells[cell].reachedUpper()) {
              cells[cell].upMax = cells[previous].upMax;
              cells[cell].upCount = cells[previous].upCount;
              cells[cell].upCount -= cells[previous].upMax;
            }
            cells[cell].isLit = false;
            cells[cell].isVisible = false;
          }
        }

        // STEP 5 - nuke lower if previous lower
        if (previous >= 0) {
          if (cells[previous].reachedLower()) {
            cells[cell].lowMax = cells[previous].lowMax;
            cells[cell].lowCount = cells[previous].lowCount;
            cells[cell].lowCount -= cells[previous].lowMax;
            cells[previous].lowCount = cells[previous].lowMax = 0;
          }

          if (cells[previous].lowMax != 0
              || (!cells[previous].isLit && cells[previous].lowMax == 0)) {
            cells[cell].lowCount = cells[cell].lowMax + 10;
          }
        }

        // STEP 6 - light up if we've reached lower bound
        if (cells[cell].reachedLower()) {
          cells[cell].isLit = true;
        }

        // now place appropriate value in sh
        if (cells[cell].isLit || (blocker && cells[cell].isVisible)) {
          result.add(location);
        }

        if (cells[cell].isLit) {
          isRowDark = false;
        }
      } // end for - cells

      isCornerVisible = false; // don't carry over to next row. :)
      if (isRowDark) {
        isAllDark = true;
      }
    } // end for - rows
  }

  public Set<Vector2D> getTemplate(Vector2D origin,
      Closure<Vector2D, Boolean> scanner, int radius) {

    if (origin == null || radius < 1 || scanner == null) {
      throw new IllegalArgumentException();
    }

    this.origin = origin;
    this.scanner = scanner;
    this.radius = radius;

    circle = CIRCLES[radius];
    result = new HashSet<Vector2D>();
    result.add(origin);

    for (int octant = 0; octant < 8; octant++) {
      scanOctant(octant);
    }

    return result;
  }

  private class Cell {

    int upCount;
    int upMax;
    int lowCount;
    int lowMax;
    boolean isLit;
    boolean isLitDelay;
    boolean isVisible; // for blockers only

    {
      init();
    }

    void init() {
      upCount = 0;
      upMax = 0;
      lowCount = 0;
      lowMax = 0;
      isLit = true;
      isVisible = true;
      isLitDelay = false;
    }

    boolean reachedLower() {
      // integer math: a 'step' has a value of 10
      // see if we're within a half step of the max. VERY important
      // to use 'half step' or else things look really stupid.
      return lowMax != 0 && lowCount + 5 >= lowMax && lowCount - 5 < lowMax;
    }

    boolean reachedUpper() {
      // see if we're within a half step of the max. VERY important
      // to use 'half step' or else things look really stupid.
      return upMax != 0 && upCount + 5 >= upMax && upCount - 5 < upMax;
    }

  }

}
