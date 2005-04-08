package ironfist.vision;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.floor;
import ironfist.loop.Level;
import ironfist.math.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Recursive field-of-view class implementing a spiraling shadow-casting
 * algorithm. This algorithm chosen because it can establish field-of-view by
 * visiting each grid at most once, and is (for me) much simpler to implement
 * than octant oriented or non-recursive approaches. -TSS
 */
public class SpiralVision {

  /**
   * Compute and return the list of RLPoints in line-of-sight to the given
   * region. In general, this method should be very fast.
   */
  public Set<Vector> getSeen(Vector origin, Level level, int radius) {
    if (origin == null || radius < 1 || level == null) {
      throw new IllegalArgumentException();
    }

    Set<Vector> result = new HashSet<Vector>(31);

    result.add(origin);

    new Run(level, origin, 1, radius, 0.0, 359.9, result).go();

    return result;
  }

  class Run {

    int r;
    double th1, th2;
    Set<Vector> pointSet;
    Vector ctr;
    int maxDistance;
    Level board;

    Run(Level level, Vector origin, int r, int maxDistance, double th1,
        double th2, Set<Vector> pointSet) {
      this.board = level;
      this.r = r;
      this.th1 = th1;
      this.th2 = th2;
      this.pointSet = pointSet;
      this.ctr = origin;
      this.maxDistance = maxDistance;
    }

    void go() {
      if (r < 1 || r > maxDistance) {
        throw new IllegalArgumentException();
      }
      ArrayList<ArcPoint> circle = circles.get(r);
      int circSize = circle.size();
      boolean wasObstacle = false;
      boolean foundClear = false;
      for (int i = 0; i < circSize; i++) {
        ArcPoint arcPoint = circle.get(i);
        Vector point = new Vector(ctr.getX() + arcPoint.x, ctr.getY()
            + arcPoint.y);

        // if outside the board, ignore it and move to the next one
        if (!board.contains(point)) {
          wasObstacle = true;
          continue;
        }
        // System.out.println("Theta " + arcPoint.theta + " (" + th1 + "," + th2
        // +
        // "," + r);

        if (arcPoint.lagging < th1 && arcPoint.theta != th1
            && arcPoint.theta != th2) {
          // System.out.println("< than " + arcPoint);
          continue;
        }
        if (arcPoint.leading > th2 && arcPoint.theta != th1
            && arcPoint.theta != th2) {
          // System.out.println("> than " + arcPoint);
          continue;
        }

        // Accept this point
        pointSet.add(point);

        // Check to see if we have an obstacle here
        Object terrain = board.get(point);
        // assert terrain != null : "terrain null @ " + point
        // + " in board with dims " + board.getWidth() + "x"
        // + board.getLength();
        boolean isObstacle = terrain == null; // terrain.config().check("opaque");

        // If obstacle is encountered, we start a new run from our start theta
        // to the rightTheta of the current point at radius+1
        // We then proceed to the next non-obstacle, whose leftTheta becomes
        // our new start theta
        // If the last point is an obstacle, we do not start a new Run at the
        // end.
        if (isObstacle) {
          // keep going
          if (wasObstacle) {
            continue;
          }

          // start a new run from our start to this point's right side
          else if (foundClear) {
            double runEndTheta = arcPoint.leading;
            double runStartTheta = th1;
            // System.out.println("Spawn obstacle at " + arcPoint);
            if (r < maxDistance) {
              new Run(board, ctr, r + 1, maxDistance, runStartTheta,
                runEndTheta, pointSet).go();
            }
            wasObstacle = true;
            // System.out.println("Continuing..." + (runs++) + ": " + r + "," +
            // (int)(th1) +
            // ":" + (int)(th2));
          } else {
            if (arcPoint.theta == 0.0) {
              th1 = 0.0;
            } else {
              th1 = arcPoint.leading;
            }
            // System.out.println("Adjusting start for obstacle "+th1+" at " +
            // arcPoint);
          }
        } else {
          foundClear = true;
          // we're clear of obstacle; any runs propogated from this run starts
          // at this
          // point's leftTheta
          if (wasObstacle) {
            ArcPoint last = circle.get(i - 1);
            // if (last.theta == 0.0) {
            // th1 = 0.0;
            // }
            // else {
            th1 = last.lagging;
            // }

            // System.out.println("Adjusting start for clear of obstacle "+th1+"
            // at " + arcPoint);

            wasObstacle = false;
          } else {
            wasObstacle = false;
            continue;
          }
        }
        wasObstacle = isObstacle;
      }

      if (!wasObstacle && r < maxDistance) {
        new Run(board, ctr, r + 1, maxDistance, th1, th2, pointSet).go();
      }
    }
  }

  static class ArcPoint implements Comparable<ArcPoint> {

    int x;
    int y;
    double theta;
    double leading;
    double lagging;

    public String toString() {
      return "(" + x + "," + y + ")=[theta=" + theta + ",leading=" + leading
          + ",lagging=" + lagging + "]";
    }

    double angle(double y, double x) {
      double a1 = atan2(y, x);
      if (a1 < 0) {
        a1 = 2 * PI + a1;
      }
      double a2 = 360.0 - a1 * 180.0 / PI;
      if (a2 == 360.0) {
        a2 = 0;
      }
      return a2;
    }

    ArcPoint(int dx, int dy) {
      this.x = dx;
      this.y = dy;
      theta = angle(y, x);
      // System.out.println(x + "," + y + ", theta=" + theta);
      // top left
      if (x < 0 && y < 0) {
        leading = angle(y - 0.5, x + 0.5);
        lagging = angle(y + 0.5, x - 0.5);
      }
      // bottom left
      else if (x < 0) {
        leading = angle(y - 0.5, x - 0.5);
        lagging = angle(y + 0.5, x + 0.5);
      }
      // bottom right
      else if (y > 0) {
        leading = angle(y + 0.5, x - 0.5);
        lagging = angle(y - 0.5, x + 0.5);
      }
      // top right
      else {
        leading = angle(y + 0.5, x + 0.5);
        lagging = angle(y - 0.5, x - 0.5);
      }

    }

    public int compareTo(ArcPoint o) {
      return theta > o.theta ? 1 : -1;
    }

    public boolean equals(Object o) {
      return theta == ((ArcPoint) o).theta;
    }

    public int hashCode() {
      return x * y;
    }
  }

  private static final HashMap<Integer, ArrayList<ArcPoint>> circles = new HashMap<Integer, ArrayList<ArcPoint>>();

  static {

    Vector origin = new Vector(0, 0);
    long t1 = System.currentTimeMillis();

    for (int i = -50; i <= 50; i++) {
      for (int j = -50; j <= 50; j++) {
        int distance = (int) floor(origin.distance(new Vector(i, j)));

        // If filled, add anything where floor(distance) <= radius
        // If not filled, require that floor(distance) == radius
        if (distance <= 50) {
          ArrayList<ArcPoint> circ = circles.get(distance);
          if (circ == null) {
            circ = new ArrayList<ArcPoint>();
            circles.put(distance, circ);
          }
          circ.add(new ArcPoint(i, j));
        }
      }
    }

    for (ArrayList<ArcPoint> list : circles.values()) {
      Collections.sort(list);
      // System.out.println("r: "+r+" "+list);
    }

    System.out.println("Circles cached after "
        + (System.currentTimeMillis() - t1));
  }

}
