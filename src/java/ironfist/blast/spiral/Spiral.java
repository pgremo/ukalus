package ironfist.blast.spiral;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import ironfist.blast.Blast;
import ironfist.math.Vector2D;
import ironfist.util.Closure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Recursive field-of-view class implementing a spiraling shadow-casting
 * algorithm. This algorithm chosen because it can establish field-of-view by
 * visiting each grid at most once, and is (for me) much simpler to implement
 * than octant oriented or non-recursive approaches. -TSS
 */
public class Spiral implements Blast {

  private static final HashMap<Integer, List<ArcPoint>> CIRCLES = new HashMap<Integer, List<ArcPoint>>();

  static {
    Vector2D origin = Vector2D.get(0, 0);

    for (int i = -20; i <= 20; i++) {
      for (int j = -20; j <= 20; j++) {
        int distance = (int) Math.floor(origin.distance(Vector2D.get(i, j)));

        // If filled, add anything where floor(distance) <= radius
        // If not filled, require that floor(distance) == radius
        if (distance <= 20) {
          List<ArcPoint> circle = CIRCLES.get(distance);
          if (circle == null) {
            circle = new ArrayList<ArcPoint>();
            CIRCLES.put(distance, circle);
          }
          circle.add(new ArcPoint(i, j));
        }
      }
    }

    for (List<ArcPoint> list : CIRCLES.values()) {
      Collections.sort(list);
    }

  }

  private Closure<Vector2D, Boolean> scanner;
  private Vector2D origin;
  private int radius;

  private Set<Vector2D> result;

  public Set<Vector2D> getTemplate(Vector2D origin,
      Closure<Vector2D, Boolean> scanner, int radius) {
    
    if (origin == null || radius < 1 || scanner == null) {
      throw new IllegalArgumentException();
    }

    this.scanner = scanner;
    this.origin = origin;
    this.radius = radius;

    result = new HashSet<Vector2D>(31);
    result.add(origin);

    process(1, 0.0, 359.9);

    return result;
  }

  void process(int r, double th1, double th2) {
    List<ArcPoint> circle = CIRCLES.get(r);
    int circSize = circle.size();
    boolean wasObstacle = false;
    boolean foundClear = false;
    for (int i = 0; i < circSize; i++) {
      ArcPoint arcPoint = circle.get(i);
      Vector2D point = origin.add(Vector2D.get(arcPoint.x, arcPoint.y));

      if (arcPoint.lagging < th1 && arcPoint.theta != th1
          && arcPoint.theta != th2) {
        continue;
      }
      if (arcPoint.leading > th2 && arcPoint.theta != th1
          && arcPoint.theta != th2) {
        continue;
      }

      // Accept this point
      result.add(point);

      // Check to see if we have an obstacle here
      boolean isObstacle = scanner.apply(point);

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
          if (r < radius) {
            process(r + 1, th1, arcPoint.leading);
          }
          wasObstacle = true;
        } else {
          if (arcPoint.theta == 0.0) {
            th1 = 0.0;
          } else {
            th1 = arcPoint.leading;
          }
        }
      } else {
        foundClear = true;
        // we're clear of obstacle; any runs propogated from this run starts
        // at this
        // point's leftTheta
        if (wasObstacle) {
          ArcPoint last = circle.get(i - 1);
          th1 = last.lagging;
          wasObstacle = false;
        } else {
          wasObstacle = false;
          continue;
        }
      }
      wasObstacle = isObstacle;
    }

    if (!wasObstacle && r < radius) {
      process(r + 1, th1, th2);
    }
  }

  private static class ArcPoint implements Comparable<ArcPoint> {

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

}
