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

  void setG(int cost);

  int getG();

  void setH(int estimage);

  int getH();

  int getF();
}