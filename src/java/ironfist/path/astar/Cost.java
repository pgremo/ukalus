/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.path.astar;

/**
 * @author gremopm
 *  
 */
public interface Cost {

  int calculate(Node previous, Node current);

}