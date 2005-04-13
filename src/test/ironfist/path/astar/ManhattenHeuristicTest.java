/*
 * Created on Feb 15, 2005
 *
 */
package ironfist.path.astar;

import ironfist.math.Vector;
import ironfist.path.astar.Heuristic;
import junit.framework.TestCase;

/**
 * @author gremopm
 *  
 */
public class ManhattenHeuristicTest extends TestCase {

  public void testDistance() {
    Node2D start = new Node2D(null, new Vector(0, 0), null);
    Node2D stop = new Node2D(null, new Vector(2, 2), null);
    Heuristic heuristic = new ManhattenHeuristic(start, stop);
    Node2D closer = new Node2D(null, new Vector(1, 1), null);
    Node2D further = new Node2D(null, new Vector(0, 2), null);
    assertEquals(2.0, heuristic.estimate(closer), 0.0);
    assertEquals(2.004, heuristic.estimate(further), 0.0);
  }

}