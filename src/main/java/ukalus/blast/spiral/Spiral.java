package ukalus.blast.spiral;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import ukalus.blast.Blast;
import ukalus.math.Vector2D;
import ukalus.util.Closure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Spiral implements Blast {

  private static final ArcPoint[][] CIRCLES;

  static {
    Map<Integer, SortedSet<ArcPoint>> circles = new HashMap<Integer, SortedSet<ArcPoint>>();
    Vector2D origin = Vector2D.get(0, 0);

    for (int i = -20; i <= 20; i++) {
      for (int j = -20; j <= 20; j++) {
        int distance = (int) (origin.distance(Vector2D.get(i, j)) + 0.5);
        if (distance <= 20) {
          SortedSet<ArcPoint> circle = circles.get(distance);
          if (circle == null) {
            circle = new TreeSet<ArcPoint>();
            circles.put(distance, circle);
          }
          circle.add(new ArcPoint(i, j));
        }
      }
    }

    CIRCLES = new ArcPoint[circles.size()][];
    for (int i = 0; i < circles.size(); i++) {
      SortedSet<ArcPoint> points = circles.get(i);
      CIRCLES[i] = points.toArray(new ArcPoint[points.size()]);
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

    scanCircle(1, 0.0, 359.9);

    return result;
  }

  void scanCircle(int r, double th1, double th2) {
    ArcPoint[] circle = CIRCLES[r];
    boolean wasBlocked = false;
    boolean isClear = false;
    for (int i = 0; i < circle.length; i++) {
      ArcPoint arcPoint = circle[i];

      if (arcPoint.theta != th1 && arcPoint.theta != th2) {
        if (arcPoint.leading > th2 || arcPoint.lagging < th1) {
          continue;
        }
      }

      Vector2D point = origin.add(Vector2D.get(arcPoint.x, arcPoint.y));
      result.add(point);
      boolean isBlocked = scanner.apply(point);

      if (isBlocked) {
        if (wasBlocked) {
          continue;
        } else if (isClear) {
          if (r < radius) {
            scanCircle(r + 1, th1, arcPoint.leading);
          }
          wasBlocked = true;
        } else {
          if (arcPoint.theta == 0.0) {
            th1 = 0.0;
          } else {
            th1 = arcPoint.leading;
          }
        }
      } else {
        isClear = true;
        if (wasBlocked) {
          th1 = circle[i - 1].lagging;
          wasBlocked = false;
        } else {
          wasBlocked = false;
          continue;
        }
      }
      wasBlocked = isBlocked;
    }

    if (!wasBlocked && r < radius) {
      scanCircle(r + 1, th1, th2);
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
