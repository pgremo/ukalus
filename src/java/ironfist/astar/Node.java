/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.astar;

/**
 * @author gremopm
 *  
 */
public interface Node {

  Node getParent();

  Node[] getSuccessors();

  void setCost(double cost);

  double getCost();

  void setEstimate(double estimage);

  double getEstimate();

  double getTotal();
}