/*
 * Created on Feb 10, 2005
 *
 */
package ukalus.path.astar;

/**
 * @author gremopm
 *  
 */
public interface Heuristic {

  double estimate(Node current);

}