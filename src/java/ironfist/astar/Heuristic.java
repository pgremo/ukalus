/*
 * Created on Feb 10, 2005
 *
 */
package ironfist.astar;

/**
 * @author gremopm
 *  
 */
public interface Heuristic {

  double estimate(Node current);

}