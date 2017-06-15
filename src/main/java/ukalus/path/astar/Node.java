/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar;

import java.util.Collection;

/**
 * @author gremopm
 *  
 */
public interface Node {

  Node getParent();

  Collection<Node> getSuccessors();

  void setCost(double cost);

  double getCost();

  void setEstimate(double estimage);

  double getEstimate();

  double getTotal();
}