/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.astar;

/**
 * @author gremopm
 *  
 */
public class ManhattenHeuristic implements Heuristic {

  private Node2D start;
  private Node2D stop;

  public ManhattenHeuristic(Node2D start, Node2D stop) {
    this.start = start;
    this.stop = stop;
  }

  public double estimate(Node current) {
    Node2D n = (Node2D) current;
    double heuristic = (Math.abs(n.getLocation()
      .getX() - stop.getLocation()
      .getX()) + Math.abs(n.getLocation()
      .getY() - stop.getLocation()
      .getY()));
    double dx1 = n.getLocation()
      .getX() - stop.getLocation()
      .getX();
    double dy1 = n.getLocation()
      .getY() - stop.getLocation()
      .getY();
    double dx2 = start.getLocation()
      .getX() - stop.getLocation()
      .getX();
    double dy2 = start.getLocation()
      .getY() - stop.getLocation()
      .getY();
    double cross = Math.abs(dx1 * dy2 - dx2 * dy1);
    heuristic += cross * 0.001;
    return heuristic;
  }
}