/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.path.astar;

/**
 * @author gremopm
 *  
 */
public interface Heuristic {

  double estimate(Node current);

}