/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar;

import ukalus.math.Vector2D;

/**
 * @author gremopm
 * 
 */
public class ManhattenHeuristic implements Heuristic {

  private Vector2D stop;
  private Vector2D v2;

  public ManhattenHeuristic(Vector2D start, Vector2D stop) {
    this.stop = stop;
    this.v2 = start.subtract(stop);
  }

  public double estimate(Node current) {
    Vector2D v1 = ((Node2D) current).getLocation()
      .subtract(stop);
    double heuristic = Math.abs(v1.getX()) + Math.abs(v1.getY());
    double cross = Math.abs(v1.getX() * v2.getY() - v2.getX() * v1.getY());
    heuristic += cross * 0.001;
    return heuristic;
  }
}