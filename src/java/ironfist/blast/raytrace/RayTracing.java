package ironfist.blast.raytrace;

import ironfist.math.Vector;
import ironfist.util.Closure;

import java.util.HashSet;
import java.util.Set;

public class RayTracing {

  private static final int MAX_LIGHT_RADIUS = 20;
  private static final int CIRC_MAX = 32000;
  private static final int BIG_SHADOW = 32000;
  private static final int MINSEE = 11;

  // the following two constants represent the 'middle' of the sh array.
  // since the current shown area is 19x19, centering the view at (9,9)
  // means it will be exactly centered.
  // This is done to accomodate possible future changes in viewable screen
  // area - simply change sh_xo and sh_yo to the new view center.

//  private static final int sh_xo = 9; // X and Y origins for the sh array
//  private static final int sh_yo = 9;

  // the Cell class, used in the shadow-casting LOS algorithm
  class Cell {

    int up_count;
    int up_max;
    int low_count;
    int low_max;
    boolean lit;
    boolean lit_delay;
    boolean visible; // for blockers only

    {
      init();
    }

    void init() {
      up_count = 0;
      up_max = 0;
      low_count = 0;
      low_max = 0;
      lit = true;
      visible = true;
      lit_delay = false;
    }

    boolean reachedLower() {
      // integer math: a 'step' has a value of 10
      // see if we're within a half step of the max. VERY important
      // to use 'half step' or else things look really stupid.
      return low_max != 0 && low_count + 5 >= low_max
          && low_count - 5 < low_max;
    }

    boolean reachedUpper() {
      // see if we're within a half step of the max. VERY important
      // to use 'half step' or else things look really stupid.
      return up_max != 0 && up_count + 5 >= up_max && up_count - 5 < up_max;
    }

  }

  // the cell array
  private Cell[] cells = new Cell[MAX_LIGHT_RADIUS];

  // the 'circle' array. For any given row, we won't check higher than
  // this given cell.
  private int[] circle = new int[MAX_LIGHT_RADIUS + 1];

  // current light radius
  private int LR = 0;

  // View constant
  static int view = 2; // 1=widest LOS .. 5=narrowest

  {
    for (int i = 0; i < cells.length; i++) {
      cells[i] = new Cell();
    }
  }

  // initialize LOS code for a given light radius
  public void setRadius(int newLR) {
    // sanity check - also allows multiple calls w/out performance loss
    if (LR != newLR) {
      LR = newLR;
      // cells should already be initted. calculate the circle array.

      // note that rows 0 and 1 will always go to infinity.
      circle[0] = circle[1] = CIRC_MAX;

      // for the rest, simply calculate max height based on radius.
      for (int i = 2; i <= LR; i++) {
        // check top
        if (2 * i * i <= LR * LR) {
          circle[i] = CIRC_MAX;
        } else {
          for (int j = i - 1; j >= 0; j--) {
            // check that Distance (I^2 + J^2) is no more than (R+0.5)^2
            // this rounding allows for *much* better looking circles.
            if (i * i + j * j <= LR * LR + LR) {
              circle[i] = j;
              break;
            }
          }
        }
      }
    }
  }

  private int calcUpper(int bX, int bY) {
    // got a blocker at row bX, cell bY. do all values
    // and scale by a factor of 10 for the integer math.
    int result = (10 * (10 * bX - view)) / (10 * bY + view);
    if (result < 10) {// upper bound for blocker on diagonal
      result = 10;
    }

    return result;
  }

  private int calcLower(int bX, int bY) {
    // got a blocker at row bX, cell bY. do all values
    // and scale by a factor of 10 for the integer math.
    int result = BIG_SHADOW;
    if (bY != 0) {
      result = (10 * (10 * bX + view)) / (10 * bY - view);
    }

    return result;
  }

  // for easy x,y octant translation
  private static final int[] xxcomp = new int[]{1, 0, 0, -1, -1, 0, 0, 1};
  private static final int[] xycomp = new int[]{0, 1, -1, 0, 0, -1, 1, 0};
  private static final int[] yxcomp = new int[]{0, 1, 1, 0, 0, -1, -1, 0};
  private static final int[] yycomp = new int[]{1, 0, 0, 1, -1, 0, 0, -1};

  private void scanOctant(int o, Closure<Vector, Boolean> scanner, int x_p,
      int y_p, Set<Vector> result) {
    // init cell[0]. this is the only one that needs clearing.
    cells[0].init();
    boolean all_dark = false;
    boolean vis_corner = false;

    // loop through each row
    for (int row = 1; row <= LR; row++) {
      boolean row_dark = true;

      // loop through each cell, up to the max allowed by circle[]
      int top = circle[row];
      if (top > row) {
        top = row;
      }

      for (int cell = 0; cell <= top; cell++) {
        // translate X,Y co'ord + bounds check
        int tx = row * xxcomp[o] + cell * xycomp[o];
        int ty = row * yxcomp[o] + cell * yycomp[o];

        if (x_p + tx < 0 || x_p + tx > 79 || y_p + ty < 0 || y_p + ty > 69) {
          continue;
        }

        // check for all_dark - we've finished the octant but
        // have yet to fill in '0' for the rest of the sight grid
        if (all_dark) {
          result.remove(new Vector(tx, ty));
          continue;
        }

        // get grid value.. see if it blocks LOS
        boolean blocker = scanner.apply(new Vector(x_p + tx, y_p + ty));

        int up_inc = 10;
        int low_inc = 10;
        int south = cell - 1;

        // STEP 1 - inherit values from immediate West, if possible
        if (cell < row) {
          // check for delayed lighting
          if (cells[cell].lit_delay) {
            if (!blocker) { // blockers don't light up with lit_delay.
              if (cells[south].lit) {
                if (cells[south].low_max != 0) {
                  cells[cell].lit = false;
                  // steal lower values
                  cells[cell].low_max = cells[south].low_max;
                  cells[cell].low_count = cells[south].low_count;
                  cells[south].low_count = 0;
                  cells[south].low_max = 0;
                  low_inc = 0; // avoid double-inc.
                } else {
                  cells[cell].lit = true;
                }
              }
            }
            cells[cell].lit_delay = false;
          }
        } else {
          // initialize new cell.
          cells[cell].init();
        }

        // STEP 2 - check for blocker
        // a dark blocker in shadow's edge will be visible
        if (blocker) {
          if (cells[cell].lit || (cell != 0 && cells[south].lit) || vis_corner) {
            // hack: make 'corners' visible
            vis_corner = cells[cell].lit;

            cells[cell].lit = false;
            cells[cell].visible = true;

            int upper = calcUpper(row, cell);
            int lower = calcLower(row, cell);

            if (upper < cells[cell].up_max || cells[cell].up_max == 0) {
              // new upper shadow
              cells[cell].up_max = upper;
              cells[cell].up_count = 0;
              up_inc = 0;
            }

            if (lower > cells[cell].low_max || cells[cell].low_max == 0) {
              // new lower shadow
              cells[cell].low_max = lower;
              cells[cell].low_count = -10;
              low_inc = 0;
              if (lower <= 30) { // somewhat arbitrary
                cells[cell].lit_delay = true;
              }
              // set dark_delay if lower > 20?? how to decide?
            }
          } else {
            cells[cell].visible = false;
          }
        } else {
          cells[cell].visible = false; // special flags for blockers
        }

        // STEP 3 - add increments to upper, lower counts
        cells[cell].up_count += up_inc;
        cells[cell].low_count += low_inc;

        // STEP 4 - check south for dark
        if (south >= 0) {
          if (cells[south].reachedUpper()) {
            if (!cells[cell].reachedUpper()) {
              cells[cell].up_max = cells[south].up_max;
              cells[cell].up_count = cells[south].up_count;
              cells[cell].up_count -= cells[south].up_max;
            }
            cells[cell].lit = false;
            cells[cell].visible = false;
          }
        }

        // STEP 5 - nuke lower if south lower
        if (south >= 0) {
          if (cells[south].reachedLower()) {
            cells[cell].low_max = cells[south].low_max;
            cells[cell].low_count = cells[south].low_count;
            cells[cell].low_count -= cells[south].low_max;
            cells[south].low_count = cells[south].low_max = 0;
          }

          if (cells[south].low_max != 0
              || (!cells[south].lit && cells[south].low_max == 0)) {
            cells[cell].low_count = cells[cell].low_max + 10;
          }
        }

        // STEP 6 - light up if we've reached lower bound
        if (cells[cell].reachedLower()) {
          cells[cell].lit = true;
        }

        // now place appropriate value in sh
        if (cells[cell].lit || (blocker && cells[cell].visible)) {
          result.add(new Vector(x_p + tx, y_p + ty));
        }

        if (cells[cell].lit) {
          row_dark = false;
        }
      } // end for - cells

      vis_corner = false; // don't carry over to next row. :)
      if (row_dark) {
        all_dark = true;
      }
    } // end for - rows
  }

  public Set<Vector> solve(int radius, Closure<Vector, Boolean> scanner,
      int x_p, int y_p) {
    Set<Vector> result = new HashSet<Vector>();
    result.add(new Vector(x_p, y_p));
    setRadius(radius);
    for (int o = 0; o < 8; o++) {
      scanOctant(o, scanner, x_p, y_p, result);
    }
    return result;
  }

}
