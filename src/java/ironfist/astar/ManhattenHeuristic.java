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

  public int estimate(Node current, Node stop) {
    Node2D n = (Node2D) current;
    Node2D goal = (Node2D) stop;
    return (Math.abs(n.getX() - goal.getX()) + Math.abs(n.getY() - goal.getY()));
  }
}