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
    double heuristic = (Math.abs(n.getX() - stop.getX()) + Math.abs(n.getY()
        - stop.getY()));
    int dx1 = n.getX() - stop.getX();
    int dy1 = n.getY() - stop.getY();
    int dx2 = start.getX() - stop.getX();
    int dy2 = start.getY() - stop.getY();
    int cross = Math.abs(dx1 * dy2 - dx2 * dy1);
    heuristic += cross * 0.001;
    return heuristic;
  }
}