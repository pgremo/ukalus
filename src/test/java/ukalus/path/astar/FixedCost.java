/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar;

/**
 * @author gremopm
 * 
 */
public class FixedCost implements Cost {

  private int cost;

  public FixedCost(int cost) {
    this.cost = cost;
  }

  public double calculate(Node previous, Node current) {
    return cost;
  }

}