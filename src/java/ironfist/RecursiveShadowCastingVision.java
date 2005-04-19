package ironfist;

import ironfist.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class RecursiveShadowCastingVision {

  private Level level;
  private int maxRadius;
  private List<Tile> seen;
  private int xCenter;
  private int yCenter;

  /**
   * DOCUMENT ME!
   * 
   * @param x1
   *          DOCUMENT ME!
   * @param y1
   *          DOCUMENT ME!
   * @param x2
   *          DOCUMENT ME!
   * @param y2
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private double slope(double x1, double y1, double x2, double y2) {
    double xDiff = x1 - x2;
    double yDiff = y1 - y2;

    return (yDiff != 0) ? xDiff / yDiff : 0;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param x1
   *          DOCUMENT ME!
   * @param y1
   *          DOCUMENT ME!
   * @param x2
   *          DOCUMENT ME!
   * @param y2
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private double invSlope(double x1, double y1, double x2, double y2) {
    double slope = slope(x1, y1, x2, y2);

    return (slope != 0) ? 1 / slope : 0;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param distance
   *          DOCUMENT ME!
   * @param startSlope
   *          DOCUMENT ME!
   * @param endSlope
   *          DOCUMENT ME!
   */
  private void scanNW2N(int distance, double start, double end) {
    double startSlope = start;
    double endSlope = end;
    if (distance > maxRadius) {
      return;
    }

    // calculate start and end cell of the scan
    int xStart = (int) (xCenter + 0.5 - (startSlope * distance));
    int xEnd = (int) (xCenter + 0.5 - (endSlope * distance));
    int yCheck = yCenter - distance;

    // is the starting cell the leftmost cell in the octant?
    // NO: call applyCell() to starting cell
    // YES: it has already been applied in start()
    if (xStart != (xCenter - (1 * distance))) {
      applyCell(xStart, yCheck);
    }

    // find out if starting cell blocks LOS
    boolean prevBlocked = scanCell(xStart, yCheck);

    // scan from the cell after the starting cell (xStart+1) to end cell of
    // scan (xCheck<=xEnd)
    for (int xCheck = xStart + 1; xCheck <= xEnd; xCheck++) {
      // is the current cell the rightmost cell in the octant?
      // NO: call applyCell() to current cell
      // YES: it has already been applied in start()
      if (xCheck != xCenter) {
        // apply cell
        applyCell(xCheck, yCheck);
      }

      // cell blocks LOS
      // if previous cell didn't block LOS (prevBlocked==false) we have
      // hit a 'new' section of walls. a new scan will be started with an
      // endSlope that 'brushes' by to the left of the blocking cell
      //
      // +---+a####+---+ @ = [xCenter+0.5,yCenter+0.5]
      // | |#####| | a = old [xCheck,yCheck]
      // | |#####| | b = new [xCheck-0.00001,yCheck+0.99999]
      // | |#####| |
      // +---b#####+---+
      // +---++---++---+
      // | || || |
      // | || || @ |
      // | || || |
      // +---++---++---+
      //
      if (scanCell(xCheck, yCheck)) {
        if (!prevBlocked) {
          scanNW2N(distance + 1, startSlope, slope(xCenter + 0.5,
            yCenter + 0.5, xCheck - 0.000001, yCheck + 0.999999));
        }

        prevBlocked = true;
      }
      // cell doesn't block LOS
      // if the cell is the first non-blocking cell after a section of walls
      // we need to calculate a new startSlope that 'brushes' by to the right
      // of the blocking cells
      //
      // #####a---++---+ @ = [xCenter+0.5,yCenter+0.5]
      // #####| || | a = new and old [xCheck,yCheck]
      // #####| || |
      // #####| || |
      // #####+---++---+
      // +---++---++---+
      // | || || |
      // | || || @ |
      // | || || |
      // +---++---++---+
      //
      else {
        if (prevBlocked) {
          startSlope = slope(xCenter + 0.5, yCenter + 0.5, xCheck, yCheck);
        }

        prevBlocked = false;
      }
    }

    // if the last cell of the scan didn't block LOS a new scan should be
    // started
    if (!prevBlocked) {
      scanNW2N(distance + 1, startSlope, endSlope);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param distance
   *          DOCUMENT ME!
   * @param startSlope
   *          DOCUMENT ME!
   * @param endSlope
   *          DOCUMENT ME!
   */
  private void scanNE2N(int distance, double start, double end) {
    double startSlope = start;
    double endSlope = end;
    if (distance > maxRadius) {
      return;
    }

    // calculate start and end cell of the scan
    int xStart = (int) (xCenter + 0.5 - (startSlope * distance));
    int xEnd = (int) (xCenter + 0.5 - (endSlope * distance));
    int yCheck = yCenter - distance;

    // is starting cell the rightmost cell in the octant?
    // NO: call applyCell() to starting cell
    // YES: it has already been applied in start()
    if (xStart != (xCenter - (-1 * distance))) {
      applyCell(xStart, yCheck);
    }

    // find out if starting cell blocks LOS
    boolean prevBlocked = scanCell(xStart, yCheck);

    // scan from the cell after the starting cell (xStart-1) to end cell of
    // scan (xCheck>=xEnd)
    for (int xCheck = xStart - 1; xCheck >= xEnd; xCheck--) {
      // is the current cell the leftmost cell in the octant?
      // NO: call applyCell() to current cell
      // YES: it has already been applied in start()
      if (xCheck != xCenter) {
        // apply cell
        applyCell(xCheck, yCheck);
      }

      // cell blocks LOS
      // if previous cell didn't block LOS (prevBlocked==false) we have
      // hit a 'new' section of walls. a new scan will be started with an
      // endSlope that 'brushes' by to the right of the blocking cell
      //
      // +---+a####+---+ @ = [xCenter+0.5,yCenter+0.5]
      // | |#####| | a = old [xCheck,yCheck]
      // | |#####| | b = new [xCheck+0.9999,yCheck-0.00001]
      // | |#####| |
      // +---+#####b---+
      // +---++---++---+
      // | || || |
      // | @ || || |
      // | || || |
      // +---++---++---+
      //
      if (scanCell(xCheck, yCheck)) {
        if (!prevBlocked) {
          scanNE2N(distance + 1, startSlope, slope(xCenter + 0.5,
            yCenter + 0.5, (double) xCheck + 1, yCheck + 0.99999));
        }

        prevBlocked = true;
      }
      // cell doesn't block LOS
      // if the cell is the first non-blocking cell after a section of walls
      // we need to calculate a new startSlope that 'brushes' by to the left
      // of the blocking cells
      //
      // +---+a---b##### @ = [xCenter+0.5,yCenter+0.5]
      // | || |##### a = old [xCheck,yCheck]
      // | || |##### b = new [xCheck+0.99999,yCheck]
      // | || |#####
      // +---++---+#####
      // +---++---++---+
      // | || || |
      // | @ || || |
      // | || || |
      // +---++---++---+
      //
      else {
        if (prevBlocked) {
          startSlope = slope(xCenter + 0.5, yCenter + 0.5, xCheck + 0.9999999,
            yCheck);
        }

        prevBlocked = false;
      }
    }

    // if the last cell of the scan didn't block LOS a new scan should be
    // started
    if (!prevBlocked) {
      scanNE2N(distance + 1, startSlope, endSlope);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param distance
   *          DOCUMENT ME!
   * @param startSlope
   *          DOCUMENT ME!
   * @param endSlope
   *          DOCUMENT ME!
   */
  private void scanNW2W(int distance, double start, double end) {
    double startSlope = start;
    double endSlope = end;
    if (distance > maxRadius) {
      return;
    }

    // calculate start and end cell of the scan
    int yStart = (int) (yCenter + 0.5 - (startSlope * distance));
    int yEnd = (int) (yCenter + 0.5 - (endSlope * distance));
    int xCheck = xCenter - distance;

    // is starting cell the topmost cell in the octant?
    // NO: call applyCell() to starting cell
    // YES: it has already been applied in start()
    if (yStart != (yCenter - (1 * distance))) {
      applyCell(xCheck, yStart);
    }

    // find out if starting cell blocks LOS
    boolean prevBlocked = scanCell(xCheck, yStart);

    // scan from the cell after the starting cell (yStart+1) to end cell of
    // scan (yCheck<=yEnd)
    for (int yCheck = yStart + 1; yCheck <= yEnd; yCheck++) {
      // is the current cell the bottommost cell in the octant?
      // NO: call applyCell() to current cell
      // YES: it has already been applied in start()
      if (yCheck != yCenter) {
        // apply cell
        applyCell(xCheck, yCheck);
      }

      // cell blocks LOS
      // if previous cell didn't block LOS (prevBlocked==false) we have
      // hit a 'new' section of walls. a new scan will be started with an
      // endSlope that 'brushes' by the top of the blocking cell (see fig.)
      //
      // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
      // | || || | a = old [xCheck,yCheck]
      // | || || | b = new [xCheck+0.99999,yCheck-0.00001]
      // | || || |
      // +---b+---++---+
      // a####+---++---+
      // #####| || |
      // #####| || |
      // #####| || |
      // #####+---++---+
      // +---++---++---+
      // | || || |
      // | || || @ |
      // | || || |
      // +---++---++---+
      //
      if (scanCell(xCheck, yCheck)) {
        if (!prevBlocked) {
          scanNW2W(distance + 1, startSlope, invSlope(xCenter + 0.5,
            yCenter + 0.5, xCheck + 0.99999, yCheck - 0.00001));
        }

        prevBlocked = true;
      }
      // cell doesn't block LOS
      // if the cell is the first non-blocking cell after a section of walls
      // we need to calculate a new startSlope that 'brushes' by the bottom
      // of the blocking cells
      //
      // #####+---++---+ @ = [xCenter+0.5,yCenter+0.5]
      // #####| || | a = old and new [xCheck,yCheck]
      // #####| || |
      // #####| || |
      // #####+---++---+
      // a---++---++---+
      // | || || |
      // | || || |
      // | || || |
      // +---++---++---+
      // +---++---++---+
      // | || || |
      // | || || @ |
      // | || || |
      // +---++---++---+
      //
      else {
        if (prevBlocked) {
          startSlope = invSlope(xCenter + 0.5, yCenter + 0.5, xCheck, yCheck);
        }

        prevBlocked = false;
      }
    }

    // if the last cell of the scan didn't block LOS a new scan should be
    // started
    if (!prevBlocked) {
      scanNW2W(distance + 1, startSlope, endSlope);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param distance
   *          DOCUMENT ME!
   * @param startSlope
   *          DOCUMENT ME!
   * @param endSlope
   *          DOCUMENT ME!
   */
  private void scanSW2W(int distance, double start, double end) {
    double startSlope = start;
    double endSlope = end;
    if (distance > maxRadius) {
      return;
    }

    // calculate start and end cell of the scan
    int yStart = (int) (yCenter + 0.5 - (startSlope * distance));
    int yEnd = (int) (yCenter + 0.5 - (endSlope * distance));
    int xCheck = xCenter - distance;

    // is starting cell the bottommost cell in the octant?
    // NO: call applyCell() to starting cell
    // YES: it has already been applied in start()
    if (yStart != (yCenter - (-1 * distance))) {
      applyCell(xCheck, yStart);
    }

    // find out if starting cell blocks LOS
    boolean prevBlocked = scanCell(xCheck, yStart);

    // scan from the cell after the starting cell (yStart-1) to end cell of
    // scan (yCheck>=yEnd)
    for (int yCheck = yStart - 1; yCheck >= yEnd; yCheck--) {
      // is the current cell the topmost cell in the octant?
      // NO: call applyCell() to current cell
      // YES: it has already been applied in start()
      if (yCheck != yCenter) {
        // apply cell
        applyCell(xCheck, yCheck);
      }

      // cell blocks LOS
      // if previous cell didn't block LOS (prevBlocked==false) we have
      // hit a 'new' section of walls. a new scan will be started with an
      // endSlope that 'brushes' by the bottom of the blocking cell
      //
      // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
      // | || || | a = old [xCheck,yCheck]
      // | || || @ | b = new [xCheck+0.99999,yCheck+1]
      // | || || |
      // +---++---++---+
      // a####+---++---+
      // #####| || |
      // #####| || |
      // #####| || |
      // #####+---++---+
      // +---b+---++---+
      // | || || |
      // | || || |
      // | || || |
      // +---++---++---+
      //
      if (scanCell(xCheck, yCheck)) {
        if (!prevBlocked) {
          scanSW2W(distance + 1, startSlope, invSlope(xCenter + 0.5,
            yCenter + 0.5, xCheck + 0.99999, (double) yCheck + 1));
        }

        prevBlocked = true;
      }
      // cell doesn't block LOS
      // if the cell is the first non-blocking cell after a section of walls
      // we need to calculate a new startSlope that 'brushes' by the top of
      // the blocking cells
      //
      // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
      // | || || | a = old [xCheck,yCheck]
      // | || || @ | b = new [xCheck,yCheck+0.99999]
      // | || || |
      // +---++---++---+
      // a---++---++---+
      // | || || |
      // | || || |
      // | || || |
      // b---++---++---+
      // #####+---++---+
      // #####| || |
      // #####| || |
      // #####| || |
      // #####+---++---+
      //
      else {
        if (prevBlocked) {
          startSlope = invSlope(xCenter + 0.5, yCenter + 0.5, xCheck,
            yCheck + 0.99999);
        }

        prevBlocked = false;
      }
    }

    // if the last cell of the scan didn't block LOS a new scan should be
    // started
    if (!prevBlocked) {
      scanSW2W(distance + 1, startSlope, endSlope);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param distance
   *          DOCUMENT ME!
   * @param startSlope
   *          DOCUMENT ME!
   * @param endSlope
   *          DOCUMENT ME!
   */
  private void scanSW2S(int distance, double start, double end) {
    double startSlope = start;
    double endSlope = end;
    if (distance > maxRadius) {
      return;
    }

    // calculate start and end cell of the scan
    int xStart = (int) (xCenter + 0.5 + (startSlope * distance));
    int xEnd = (int) (xCenter + 0.5 + (endSlope * distance));
    int yCheck = yCenter + distance;

    // is the starting cell the leftmost cell in the octant?
    // NO: call applyCell() to starting cell
    // YES: it has already been applied in start()
    if (xStart != (xCenter + (-1 * distance))) {
      applyCell(xStart, yCheck);
    }

    // find out if starting cell blocks LOS
    boolean prevBlocked = scanCell(xStart, yCheck);

    // scan from the cell after the starting cell (xStart+1) to end cell of
    // scan (xCheck<=xEnd)
    for (int xCheck = xStart + 1; xCheck <= xEnd; xCheck++) {
      // is the current cell the rightmost cell in the octant?
      // NO: call applyCell() to current cell
      // YES: it has already been applied in start()
      if (xCheck != xCenter) {
        // apply cell
        applyCell(xCheck, yCheck);
      }

      // cell blocks LOS
      // if previous cell didn't block LOS (prevBlocked==false) we have
      // hit a 'new' section of walls. a new scan will be started with an
      // endSlope that 'brushes' by to the left of the blocking cell
      //
      // +---++---++---+
      // | || || |
      // | || || @ |
      // | || || |
      // +---++---++---+
      // +---ba####+---+ @ = [xCenter+0.5,yCenter+0.5]
      // | |#####| | a = old [xCheck,yCheck]
      // | |#####| | b = new [xCheck-0.00001,yCheck]
      // | |#####| |
      // +---+#####+---+
      //
      if (scanCell(xCheck, yCheck)) {
        if (!prevBlocked) {
          scanSW2S(distance + 1, startSlope, slope(xCenter + 0.5,
            yCenter + 0.5, xCheck - 0.00001, yCheck));
        }

        prevBlocked = true;
      }
      // cell doesn't block LOS
      // if the cell is the first non-blocking cell after a section of walls
      // we need to calculate a new startSlope that 'brushes' by to the right
      // of the blocking cells
      //
      // +---++---++---+
      // | || || |
      // | || || @ |
      // | || || |
      // +---++---++---+
      // #####a---++---+ @ = [xCenter+0.5,yCenter+0.5]
      // #####| || | a = old [xCheck,yCheck]
      // #####| || | b = new [xCheck,yCheck+0.99999]
      // #####| || |
      // #####b---++---+
      //
      else {
        if (prevBlocked) {
          startSlope = slope(xCenter + 0.5, yCenter + 0.5, xCheck,
            yCheck + 0.99999);
        }

        prevBlocked = false;
      }
    }

    // if the last cell of the scan didn't block LOS a new scan should be
    // started
    if (!prevBlocked) {
      scanSW2S(distance + 1, startSlope, endSlope);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param distance
   *          DOCUMENT ME!
   * @param startSlope
   *          DOCUMENT ME!
   * @param endSlope
   *          DOCUMENT ME!
   */
  private void scanSE2S(int distance, double start, double end) {
    double startSlope = start;
    double endSlope = end;
    if (distance > maxRadius) {
      return;
    }

    // calculate start and end cell of the scan
    int xStart = (int) (xCenter + 0.5 + (startSlope * distance));
    int xEnd = (int) (xCenter + 0.5 + (endSlope * distance));
    int yCheck = yCenter + distance;

    // is starting cell the rightmost cell in the octant?
    // NO: call applyCell() to starting cell
    // YES: it has already been applied in start()
    if (xStart != (xCenter + (1 * distance))) {
      applyCell(xStart, yCheck);
    }

    // find out if starting cell blocks LOS
    boolean prevBlocked = scanCell(xStart, yCheck);

    // scan from the cell after the starting cell (xStart-1) to end cell of
    // scan (xCheck>=xEnd)
    for (int xCheck = xStart - 1; xCheck >= xEnd; xCheck--) {
      // is the current cell the leftmost cell in the octant?
      // NO: call applyCell() to current cell
      // YES: it has already been applied in start()
      if (xCheck != xCenter) {
        // apply cell
        applyCell(xCheck, yCheck);
      }

      // cell blocks LOS
      // if previous cell didn't block LOS (prevBlocked==false) we have
      // hit a 'new' section of walls. a new scan will be started with an
      // endSlope that 'brushes' by to the right of the blocking cell
      //
      // +---++---++---+
      // | || || |
      // | @ || || |
      // | || || |
      // +---++---++---+
      // +---+a####b---+ @ = [xCenter+0.5,yCenter+0.5]
      // | |#####| | a = old [xCheck,yCheck]
      // | |#####| | b = new [xCheck+1,yCheck]
      // | |#####| |
      // +---+#####+---+
      //
      if (scanCell(xCheck, yCheck)) {
        if (!prevBlocked) {
          scanSE2S(distance + 1, startSlope, slope(xCenter + 0.5,
            yCenter + 0.5, (double) xCheck + 1, yCheck));
        }

        prevBlocked = true;
      }
      // cell doesn't block LOS
      // if the cell is the first non-blocking cell after a section of walls
      // we need to calculate a new startSlope that 'brushes' by to the left
      // of the blocking cells
      //
      // +---++---++---+
      // | || || |
      // | @ || || |
      // | || || |
      // +---++---++---+
      // +---+a---+##### @ = [xCenter+0.5,yCenter+0.5]
      // | || |##### a = old [xCheck,yCheck]
      // | || |##### b = new [xCheck+0.99999,yCheck+0.99999]
      // | || |#####
      // +---++---b#####
      //
      else {
        if (prevBlocked) {
          startSlope = slope(xCenter + 0.5, yCenter + 0.5, xCheck + 0.99999,
            yCheck + 0.99999);
        }

        prevBlocked = false;
      }
    }

    // if the last cell of the scan didn't block LOS a new scan should be
    // started
    if (!prevBlocked) {
      scanSE2S(distance + 1, startSlope, endSlope);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param distance
   *          DOCUMENT ME!
   * @param startSlope
   *          DOCUMENT ME!
   * @param endSlope
   *          DOCUMENT ME!
   */
  private void scanNE2E(int distance, double start, double end) {
    double startSlope = start;
    double endSlope = end;
    if (distance > maxRadius) {
      return;
    }

    // calculate start and end cell of the scan
    int yStart = (int) (yCenter + 0.5 + (startSlope * distance));
    int yEnd = (int) (yCenter + 0.5 + (endSlope * distance));
    int xCheck = xCenter + distance;

    // is starting cell the topmost cell in the octant?
    // NO: call applyCell() to starting cell
    // YES: it has already been applied in start()
    if (yStart != (yCenter + (-1 * distance))) {
      applyCell(xCheck, yStart);
    }

    // find out if starting cell blocks LOS
    boolean prevBlocked = scanCell(xCheck, yStart);

    // scan from the cell after the starting cell (yStart+1) to end cell of
    // scan (yCheck<=yEnd)
    for (int yCheck = yStart + 1; yCheck <= yEnd; yCheck++) {
      // is the current cell the bottommost cell in the octant?
      // NO: call applyCell() to current cell
      // YES: it has already been applied in start()
      if (yCheck != yCenter) {
        // apply cell
        applyCell(xCheck, yCheck);
      }

      // cell blocks LOS
      // if previous cell didn't block LOS (prevBlocked==false) we have
      // hit a 'new' section of walls. a new scan will be started with an
      // endSlope that 'brushes' by the top of the blocking cell (see fig.)
      //
      // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
      // | || || | a = old [xCheck,yCheck]
      // | || || | b = new [xCheck,yCheck-0.00001]
      // | || || |
      // +---++---+b---+
      // +---++---+a####
      // | || |#####
      // | || |#####
      // | || |#####
      // +---++---+#####
      // +---++---++---+
      // | || || |
      // | @ || || |
      // | || || |
      // +---++---++---+
      //
      if (scanCell(xCheck, yCheck)) {
        if (!prevBlocked) {
          scanNE2E(distance + 1, startSlope, invSlope(xCenter + 0.5,
            yCenter + 0.5, xCheck, yCheck - 0.00001));
        }

        prevBlocked = true;
      }
      // cell doesn't block LOS
      // if the cell is the first non-blocking cell after a section of walls
      // we need to calculate a new startSlope that 'brushes' by the bottom
      // of the blocking cells
      //
      // +---++---+##### @ = [xCenter+0.5,yCenter+0.5]
      // | || |##### a = old [xCheck,yCheck]
      // | || |##### b = new [xCheck+0.99999,yCheck]
      // | || |#####
      // +---++---+#####
      // +---++---+a---b
      // | || || |
      // | || || |
      // | || || |
      // +---++---++---+
      // +---++---++---+
      // | || || |
      // | @ || || |
      // | || || |
      // +---++---++---+
      //
      else {
        if (prevBlocked) {
          startSlope = invSlope(xCenter + 0.5, yCenter + 0.5, xCheck + 0.99999,
            yCheck);
        }

        prevBlocked = false;
      }
    }

    // if the last cell of the scan didn't block LOS a new scan should be
    // started
    if (!prevBlocked) {
      scanNE2E(distance + 1, startSlope, endSlope);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param distance
   *          DOCUMENT ME!
   * @param startSlope
   *          DOCUMENT ME!
   * @param endSlope
   *          DOCUMENT ME!
   */
  private void scanSE2E(int distance, double start, double end) {
    double startSlope = start;
    double endSlope = end;
    if (distance > maxRadius) {
      return;
    }

    // calculate start and end cell of the scan
    int yStart = (int) (yCenter + 0.5 + (startSlope * distance));
    int yEnd = (int) (yCenter + 0.5 + (endSlope * distance));
    int xCheck = xCenter + distance;

    // is starting cell the bottommost cell in the octant?
    // NO: call applyCell() to starting cell
    // YES: it has already been applied in start()
    if (yStart != (yCenter + (1 * distance))) {
      applyCell(xCheck, yStart);
    }

    // find out if starting cell blocks LOS
    boolean prevBlocked = scanCell(xCheck, yStart);

    // scan from the cell after the starting cell (yStart-1) to end cell of
    // scan (yCheck>=yEnd)
    for (int yCheck = yStart - 1; yCheck >= yEnd; yCheck--) {
      // is the current cell the topmost cell in the octant?
      // NO: call applyCell() to current cell
      // YES: it has already been applied in start()
      if (yCheck != yCenter) {
        // apply cell
        applyCell(xCheck, yCheck);
      }

      // cell blocks LOS
      // if previous cell didn't block LOS (prevBlocked==false) we have
      // hit a 'new' section of walls. a new scan will be started with an
      // endSlope that 'brushes' by the bottom of the blocking cell
      //
      // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
      // | || || | a = old [xCheck,yCheck]
      // | @ || || | b = new [xCheck,yCheck+1]
      // | || || |
      // +---++---++---+
      // +---++---+a####
      // | || |#####
      // | || |#####
      // | || |#####
      // +---++---+#####
      // +---++---+b---+
      // | || || |
      // | || || |
      // | || || |
      // +---++---++---+
      //
      if (scanCell(xCheck, yCheck)) {
        if (!prevBlocked) {
          scanSE2E(distance + 1, startSlope, invSlope(xCenter + 0.5,
            yCenter + 0.5, xCheck, (double) yCheck + 1));
        }

        prevBlocked = true;
      }
      // cell doesn't block LOS
      // if the cell is the first non-blocking cell after a section of walls
      // we need to calculate a new startSlope that 'brushes' by the top of
      // the blocking cells
      //
      // +---++---++---+ @ = [xCenter+0.5,yCenter+0.5]
      // | || || | a = old [xCheck,yCheck]
      // | @ || || | b = new [xCheck+0.99999,yCheck+0.99999]
      // | || || |
      // +---++---++---+
      // +---++---+a---+
      // | || || |
      // | || || |
      // | || || |
      // +---++---++---b
      // +---++---+#####
      // | || |#####
      // | || |#####
      // | || |#####
      // +---++---+#####
      //
      else {
        if (prevBlocked) {
          startSlope = invSlope(xCenter + 0.5, yCenter + 0.5, xCheck + 0.99999,
            yCheck + 0.99999);
        }

        prevBlocked = false;
      }
    }

    // if the last cell of the scan didn't block LOS a new scan should be
    // started
    if (!prevBlocked) {
      scanSE2E(distance + 1, startSlope, endSlope);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param map
   *          DOCUMENT ME!
   * @param x
   *          DOCUMENT ME!
   * @param y
   *          DOCUMENT ME!
   * @param maxRadius
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public List<Tile> getSeen(Level map, int x, int y, int maxRadius) {
    this.level = map;
    this.xCenter = x;
    this.yCenter = y;
    this.maxRadius = maxRadius;
    seen = new ArrayList<Tile>();

    if (map != null) {

      // apply starting cell
      applyCell(x, y);

      if (maxRadius > 0) {
        // scan and apply north
        // until a blocking cell is hit or
        // until maxRadius is reached
        int nL;

        for (nL = 1; nL <= maxRadius; nL++) {
          applyCell(x, y - nL);

          if (scanCell(x, y - nL)) {
            break;
          }
        }

        // scan and apply north east
        // until a blocking cell is hit or
        // until maxRadius is reached
        int neL;

        for (neL = 1; neL <= maxRadius; neL++) {
          applyCell(x + neL, y - neL);

          if (scanCell(x + neL, y - neL)) {
            break;
          }
        }

        // scan and apply east
        // until a blocking cell is hit or
        // until maxRadius is reached
        int eL;

        for (eL = 1; eL <= maxRadius; eL++) {
          applyCell(x + eL, y);

          if (scanCell(x + eL, y)) {
            break;
          }
        }

        // scan and apply south east
        // until a blocking cell is hit or
        // until maxRadius is reached
        int seL;

        for (seL = 1; seL <= maxRadius; seL++) {
          applyCell(x + seL, y + seL);

          if (scanCell(x + seL, y + seL)) {
            break;
          }
        }

        // scan and apply south
        // until a blocking cell is hit or
        // until maxRadius is reached
        int sL;

        for (sL = 1; sL <= maxRadius; sL++) {
          applyCell(x, y + sL);

          if (scanCell(x, y + sL)) {
            break;
          }
        }

        // scan and apply south west
        // until a blocking cell is hit or
        // until maxRadius is reached
        int swL;

        for (swL = 1; swL <= maxRadius; swL++) {
          applyCell(x - swL, y + swL);

          if (scanCell(x - swL, y + swL)) {
            break;
          }
        }

        // scan and apply west
        // until a blocking cell is hit or
        // until maxRadius is reached
        int wL;

        for (wL = 1; wL <= maxRadius; wL++) {
          applyCell(x - wL, y);

          if (scanCell(x - wL, y)) {
            break;
          }
        }

        // scan and apply north west
        // until a blocking cell is hit or
        // until maxRadius is reached
        int nwL;

        for (nwL = 1; nwL <= maxRadius; nwL++) {
          applyCell(x - nwL, y - nwL);

          if (scanCell(x - nwL, y - nwL)) {
            break;
          }
        }

        // scan the octant covering the area from north west to north
        // if it isn't blocked
        if ((nL != 1) || (nwL != 1)) {
          scanNW2N(1, 1, 0);
        }

        // scan the octant covering the area from north east to north
        // if it isn't blocked
        if ((nL != 1) || (neL != 1)) {
          scanNE2N(1, -1, 0);
        }

        // scan the octant covering the area from north west to west
        // if it isn't blocked
        if ((nwL != 1) || (wL != 1)) {
          scanNW2W(1, 1, 0);
        }

        // scan the octant covering the area from south west to west
        // if it isn't blocked
        if ((swL != 1) || (wL != 1)) {
          scanSW2W(1, -1, 0);
        }

        // scan the octant covering the area from south west to south
        // if it isn't blocked
        if ((swL != 1) || (sL != 1)) {
          scanSW2S(1, -1, 0);
        }

        // scan the octant covering the area from south east to south
        // if it isn't blocked
        if ((seL != 1) || (sL != 1)) {
          scanSE2S(1, 1, 0);
        }

        // scan the octant covering the area from north east to east
        // if it isn't blocked
        if ((neL != 1) || (eL != 1)) {
          scanNE2E(1, -1, 0);
        }

        // scan the octant covering the area from south east to east
        // if it isn't blocked
        if ((seL != 1) || (eL != 1)) {
          scanSE2E(1, 1, 0);
        }
      }
    }

    return seen;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param x
   *          DOCUMENT ME!
   * @param y
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private boolean scanCell(int x, int y) {
    Tile tile = level.get(Vector2D.get(x, y));
    TileType type = tile.getTileType();
    boolean result = true;

    if (type instanceof Floor) {
      Floor floor = (Floor) type;
      Door door = floor.getDoor();

      result = !((door == null) || door.isOpen());
    }
    return result;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param x
   *          DOCUMENT ME!
   * @param y
   *          DOCUMENT ME!
   */
  private void applyCell(int x, int y) {
    int newX = Math.abs(x - xCenter);
    int newY = Math.abs(y - yCenter);
    if ((Math.max(newX, newY) + (Math.min(newX, newY) / 2)) <= maxRadius) {
      Tile tile = level.get(Vector2D.get(x, y));

      if (tile != null) {
        seen.add(tile);
      }
    }
  }
}