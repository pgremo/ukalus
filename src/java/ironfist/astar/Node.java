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

  void setG(double cost);

  double getG();

  void setH(double estimage);

  double getH();

  double getF();
}